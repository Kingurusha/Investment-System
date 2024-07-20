package cz.cvut.nss.investmentmanagementsystem.controller;

import cz.cvut.nss.investmentmanagementsystem.model.Order;
import cz.cvut.nss.investmentmanagementsystem.model.enums.TransactionType;
import cz.cvut.nss.investmentmanagementsystem.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Controller
@RequestMapping("api/orders")
public class OrdersController {
    private final OrderService orderService;
    @Autowired
    public OrdersController(OrderService orderService) {
        this.orderService = orderService;
    }
    @GetMapping("/orders")
    public Page<Order> getOrders(@RequestParam(required = false) TransactionType transactionType,
                                 @RequestParam(required = false) BigDecimal quantity,
                                 @RequestParam(required = false) BigDecimal price,
                                 @RequestParam(required = false) LocalDateTime dateCreatedOrder,
                                 @RequestParam(required = false) String marketDataSymbol,
                                 @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size,
                                 @RequestParam(defaultValue = "dateCreatedOrder") String sortBy,
                                 @RequestParam(defaultValue = "asc") String order){
        Sort sort = Sort.by(Sort.Direction.fromString(order), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return orderService.findOrders(transactionType, quantity, price, dateCreatedOrder, marketDataSymbol, pageable);
    }
}
