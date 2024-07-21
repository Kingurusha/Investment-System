package cz.cvut.nss.investmentmanagementsystem.service.factoryorder;

import cz.cvut.nss.investmentmanagementsystem.model.Asset;
import cz.cvut.nss.investmentmanagementsystem.model.Order;
import cz.cvut.nss.investmentmanagementsystem.model.Portfolio;
import cz.cvut.nss.investmentmanagementsystem.model.User;
import cz.cvut.nss.investmentmanagementsystem.model.enums.OrderStatus;
import cz.cvut.nss.investmentmanagementsystem.model.enums.TransactionType;
import cz.cvut.nss.investmentmanagementsystem.repository.AssetRepository;
import cz.cvut.nss.investmentmanagementsystem.repository.UserRepository;
import cz.cvut.nss.investmentmanagementsystem.service.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Service
public class BuyTransactionProcessor implements TransactionProcessor {
    private final UserRepository userRepository;
    private final AssetRepository assetRepository;
    private final PortfolioService portfolioService;

    @Autowired
    public BuyTransactionProcessor(UserRepository userRepository, AssetRepository assetRepository, PortfolioService portfolioService) {
        this.userRepository = userRepository;
        this.assetRepository = assetRepository;
        this.portfolioService = portfolioService;
    }

    @Override
    public void process(Order newOrder, User orderAccepter, User orderCreator, Portfolio portfolioAccepter, Portfolio portfolioCreator) {
        if (orderAccepter.getBalance().compareTo(newOrder.getPrice().multiply(newOrder.getQuantity())) >= 0) {
            orderAccepter.setBalance(orderAccepter.getBalance().subtract(newOrder.getPrice().multiply(newOrder.getQuantity())));
            userRepository.save(orderAccepter);
            Asset buyerAsset = assetRepository.findByPortfolioIdAndMarketDataSymbol(portfolioAccepter.getId(),
                    newOrder.getMarketData().getSymbol()).orElseGet(() -> {
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

            portfolioService.rebalancePortfolio(buyerAsset.getPortfolio().getId(), buyerAsset, TransactionType.BUY);
            portfolioService.rebalancePortfolio(sellerAsset.getPortfolio().getId(), sellerAsset, TransactionType.SELL);
        }
    }
}
