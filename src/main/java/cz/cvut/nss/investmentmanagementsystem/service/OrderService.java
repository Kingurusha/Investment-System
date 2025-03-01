package cz.cvut.nss.investmentmanagementsystem.service;

import cz.cvut.nss.investmentmanagementsystem.helper.validator.OrderValidator;
import cz.cvut.nss.investmentmanagementsystem.model.Asset;
import cz.cvut.nss.investmentmanagementsystem.model.Order;
import cz.cvut.nss.investmentmanagementsystem.model.Portfolio;
import cz.cvut.nss.investmentmanagementsystem.model.User;
import cz.cvut.nss.investmentmanagementsystem.model.enums.OrderStatus;
import cz.cvut.nss.investmentmanagementsystem.model.enums.TransactionType;
import cz.cvut.nss.investmentmanagementsystem.repository.AssetRepository;
import cz.cvut.nss.investmentmanagementsystem.repository.OrderRepository;
import cz.cvut.nss.investmentmanagementsystem.repository.PortfolioRepository;
import cz.cvut.nss.investmentmanagementsystem.repository.UserRepository;
import cz.cvut.nss.investmentmanagementsystem.service.factoryorder.TransactionProcessor;
import cz.cvut.nss.investmentmanagementsystem.service.factoryorder.TransactionProcessorFactory;
import cz.cvut.nss.investmentmanagementsystem.specification.OrderSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
public class OrderService implements CrudService<Order, Long> {
    private final OrderRepository orderRepository;
    private final OrderValidator orderValidator;
    private final UserRepository userRepository;
    private final AssetRepository assetRepository;
    private final PortfolioRepository portfolioRepository;
    private final PortfolioService portfolioService;
    private final TransactionProcessorFactory transactionProcessorFactory;
    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    @Autowired
    public OrderService(OrderRepository orderRepository, OrderValidator orderValidator, UserRepository userRepository, AssetRepository assetRepository, PortfolioRepository portfolioRepository, PortfolioService portfolioService, TransactionProcessorFactory transactionProcessorFactory) {
        this.orderRepository = orderRepository;
        this.orderValidator = orderValidator;
        this.userRepository = userRepository;
        this.assetRepository = assetRepository;
        this.portfolioRepository = portfolioRepository;
        this.portfolioService = portfolioService;
        this.transactionProcessorFactory = transactionProcessorFactory;
    }

    @Override
    @Transactional
    public void create(Order order) {
        order.setOrderStatus(OrderStatus.OPEN);
        order.setDateCreatedOrder(LocalDateTime.now());
        orderRepository.save(order);
        LOG.debug("Create order {}.", order);
    }

    @Override
    @Transactional(readOnly = true)
    public Order get(Long orderId) {
        orderValidator.validateExistById(orderId);
        return orderRepository.findById(orderId).get();
    }

    @Override
    @Transactional
    public void update(Order order) {
        orderValidator.validateExistById(order.getId());
        orderRepository.save(order);
        LOG.debug("Update order {}.", order);
    }

    @Override
    @Transactional
    public void delete(Long orderId) {
        orderValidator.validateExistById(orderId);
        orderRepository.deleteById(orderId);
        LOG.debug("Delete order with ID {}.", orderId);
    }

    /**
     * Confirms an order by processing the transaction based on the order type.
     *
     * @param orderAccepterId the ID of the user accepting the order
     * @param orderId the ID of the order to confirm
     * @param portfolioOrderAccepterId the ID of the portfolio of the user accepting the order
     * @param portfolioOrderCreatorId the ID of the portfolio of the user who created the order
     */
    @Transactional
    public void confirmOrder(Long orderAccepterId, Long orderId, Long portfolioOrderAccepterId, Long portfolioOrderCreatorId) {
        Order newOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with ID: " + orderId));
        User orderAccepter = userRepository.findById(orderAccepterId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + orderAccepterId));
        User orderCreator = userRepository.findById(newOrder.getUser().getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + newOrder.getUser().getId()));
        Portfolio portfolioAccepter = portfolioRepository.findById(portfolioOrderAccepterId)
                .orElseThrow(() -> new IllegalArgumentException("Portfolio not found with ID: " + portfolioOrderAccepterId));
        Portfolio portfolioCreator = portfolioRepository.findById(portfolioOrderCreatorId)
                .orElseThrow(() -> new IllegalArgumentException("Portfolio not found with ID: " + portfolioOrderCreatorId));
        TransactionProcessor transactionProcessor = transactionProcessorFactory.getProcessor(newOrder.getTransactionType());
        transactionProcessor.process(newOrder, orderAccepter, orderCreator, portfolioAccepter, portfolioCreator);
        LOG.debug("Order confirmed: {}", newOrder);
    }

    /**
     * Finds orders based on various criteria.
     *
     * @param transactionType the type of transaction (buy or sell)
     * @param quantity the quantity of the order
     * @param price the price of the order
     * @param dateCreatedOrder the date the order was created
     * @param marketDataSymbol the symbol of the market data
     * @param pageable the pagination information
     * @return a paginated list of orders matching the criteria
     */
    @Transactional(readOnly = true)
    public Page<Order> findOrders(TransactionType transactionType, BigDecimal quantity, BigDecimal price,
                                  LocalDateTime dateCreatedOrder, String marketDataSymbol, Pageable pageable){
        Specification<Order> spec = Specification.where(null);
        if (transactionType != null) {
            spec = spec.and(OrderSpecification.hasTransactionType(transactionType));
        }
        if (quantity != null) {
            spec = spec.and(OrderSpecification.hasQuantity(quantity));
        }
        if (price != null) {
            spec = spec.and(OrderSpecification.hasPrice(price));
        }
        if (dateCreatedOrder != null) {
            spec = spec.and(OrderSpecification.hasDateCreatedOrder(dateCreatedOrder));
        }
        if (marketDataSymbol != null) {
            spec = spec.and(OrderSpecification.hasMarketDataSymbol(marketDataSymbol));
        }

        return orderRepository.findAll(spec, pageable);
    }
}

