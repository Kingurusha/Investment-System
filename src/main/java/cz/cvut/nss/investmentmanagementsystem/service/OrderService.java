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
import cz.cvut.nss.investmentmanagementsystem.specification.OrderSpecification;
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

    @Autowired
    public OrderService(OrderRepository orderRepository, OrderValidator orderValidator, UserRepository userRepository, AssetRepository assetRepository, PortfolioRepository portfolioRepository, PortfolioService portfolioService) {
        this.orderRepository = orderRepository;
        this.orderValidator = orderValidator;
        this.userRepository = userRepository;
        this.assetRepository = assetRepository;
        this.portfolioRepository = portfolioRepository;
        this.portfolioService = portfolioService;
    }

    @Override
    @Transactional
    public void create(Order order) {
        order.setOrderStatus(OrderStatus.OPEN);
        order.setDateCreatedOrder(LocalDateTime.now());
        orderRepository.save(order);
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
    }

    @Override
    @Transactional
    public void delete(Long orderId) {
        orderValidator.validateExistById(orderId);
        orderRepository.deleteById(orderId);
    }

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

        if (newOrder.getTransactionType() == TransactionType.BUY) {
            if (orderAccepter.getBalance().compareTo(newOrder.getPrice().multiply(newOrder.getQuantity())) >= 0) {
                orderAccepter.setBalance(orderAccepter.getBalance().subtract(newOrder.getPrice().multiply(newOrder.getQuantity())));
                userRepository.save(orderAccepter);
                Asset buyerAsset = assetRepository.findByPortfolioIdAndMarketDataSymbol(portfolioOrderAccepterId
                        , newOrder.getMarketData().getSymbol()).orElseGet(() -> {
                    Asset newAsset = new Asset();
                    newAsset.setMarketData(newOrder.getMarketData());
                    newAsset.setPortfolio(portfolioAccepter);
                    newAsset.setQuantity(BigDecimal.ZERO);
                    return newAsset;
                });
                Set<Order> orders = buyerAsset.getOrders();
                if (orders == null) {
                    orders = new HashSet<>();
                }
                orders.add(newOrder);
                buyerAsset.setOrders(orders);
                buyerAsset.setQuantity(buyerAsset.getQuantity().add(newOrder.getQuantity()));
                assetRepository.save(buyerAsset);

                Asset sellerAsset = assetRepository.findByPortfolioIdAndMarketDataSymbol(
                                newOrder.getAsset().getPortfolio().getId(), newOrder.getMarketData().getSymbol())
                        .orElseThrow(() -> new IllegalArgumentException("Asset not found for seller"));
                sellerAsset.setQuantity(sellerAsset.getQuantity().subtract(newOrder.getQuantity()));
                assetRepository.save(sellerAsset);

                orderCreator.setBalance(orderCreator.getBalance().add(newOrder.getQuantity().multiply(newOrder.getPrice())));
                userRepository.save(orderCreator);

                newOrder.setOrderStatus(OrderStatus.EXECUTED);
                orderRepository.save(newOrder);

                portfolioService.rebalancePortfolio(buyerAsset.getPortfolio().getId(), buyerAsset, TransactionType.BUY);
                portfolioService.rebalancePortfolio(sellerAsset.getPortfolio().getId(), sellerAsset, TransactionType.SELL);
            } else {
                throw new IllegalArgumentException("Insufficient funds for user ID: " + orderAccepterId);
            }
        } else {
            Asset sellerAsset = assetRepository.findByPortfolioIdAndMarketDataSymbol(portfolioAccepter.getId(), newOrder.getMarketData().getSymbol())
                    .orElseThrow(() -> new IllegalArgumentException("Asset not found for seller"));
            if (sellerAsset.getQuantity().compareTo(newOrder.getQuantity()) >= 0) {
                sellerAsset.setQuantity(sellerAsset.getQuantity().subtract(newOrder.getQuantity()));
                assetRepository.save(sellerAsset);

                Asset buyerAsset = assetRepository.findByUserIdAndMarketDataSymbol(orderCreator.getId()
                        , newOrder.getMarketData().getSymbol()).orElseGet(() -> {
                    Asset newAsset = new Asset();
                    newAsset.setMarketData(newOrder.getMarketData());
                    newAsset.setPortfolio(portfolioCreator);
                    newAsset.setQuantity(BigDecimal.ZERO);
                    return newAsset;
                });
                Set<Order> orders = buyerAsset.getOrders();
                if (orders == null) {
                    orders = new HashSet<>();
                }
                orders.add(newOrder);
                buyerAsset.setOrders(orders);
                buyerAsset.setQuantity(buyerAsset.getQuantity().add(newOrder.getQuantity()));

                orderCreator.setBalance(orderCreator.getBalance().subtract((newOrder.getQuantity().multiply(newOrder.getPrice()))));
                userRepository.save(orderCreator);

                newOrder.setOrderStatus(OrderStatus.EXECUTED);
                orderRepository.save(newOrder);

                portfolioService.rebalancePortfolio(portfolioOrderAccepterId, sellerAsset, TransactionType.SELL);
                portfolioService.rebalancePortfolio(portfolioOrderCreatorId, buyerAsset, TransactionType.BUY);
            } else {
                throw new IllegalArgumentException("Insufficient assets for user ID: " + orderAccepterId);
            }
        }
    }
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

