package cz.cvut.nss.investmentmanagementsystem.model.dto.convertor;

import cz.cvut.nss.investmentmanagementsystem.model.Order;
import cz.cvut.nss.investmentmanagementsystem.model.dto.OrderDto;
import cz.cvut.nss.investmentmanagementsystem.service.AssetService;
import cz.cvut.nss.investmentmanagementsystem.service.MarketDataService;
import cz.cvut.nss.investmentmanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderConvertor {
    private final UserService userService;
    private final MarketDataService marketDataService;
    private final AssetService assetService;

    @Autowired
    public OrderConvertor(UserService userService, MarketDataService marketDataService, AssetService assetService) {
        this.userService = userService;
        this.marketDataService = marketDataService;
        this.assetService = assetService;
    }
    public OrderDto toDto(Order order) {
        return new OrderDto(
                order.getId(),
                order.getTransactionType(),
                order.getOrderStatus(),
                order.getQuantity(),
                order.getPrice(),
                order.getUser().getId(),
                order.getMarketData().getId(),
                order.getAsset().getId());
    }

    public Order getEntity(OrderDto orderDto) {
        Order order = new Order();
        order.setId(orderDto.id());
        order.setTransactionType(orderDto.transactionType());
        order.setOrderStatus(orderDto.orderStatus());
        order.setQuantity(orderDto.quantity());
        order.setPrice(orderDto.price());
        order.setUser(userService.get(orderDto.userId()));
        order.setMarketData(marketDataService.get(orderDto.marketDataId()));
        order.setAsset(assetService.get(orderDto.assetId()));
        return order;
    }
}
