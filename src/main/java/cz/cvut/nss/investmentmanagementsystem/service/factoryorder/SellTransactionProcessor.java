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
public class SellTransactionProcessor implements TransactionProcessor {
    private final PortfolioService portfolioService;
    private final UserRepository userRepository;
    private final AssetRepository assetRepository;

    @Autowired
    public SellTransactionProcessor(PortfolioService portfolioService, UserRepository userRepository, AssetRepository assetRepository) {
        this.portfolioService = portfolioService;
        this.userRepository = userRepository;
        this.assetRepository = assetRepository;
    }

    @Override
    public void process(Order newOrder, User orderAccepter, User orderCreator, Portfolio portfolioAccepter, Portfolio portfolioCreator) {
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

            portfolioService.rebalancePortfolio(portfolioAccepter.getId(), sellerAsset, TransactionType.SELL);
            portfolioService.rebalancePortfolio(portfolioCreator.getId(), buyerAsset, TransactionType.BUY);
        }
    }
}
