package cz.cvut.nss.investmentmanagementsystem.controller;

import cz.cvut.nss.investmentmanagementsystem.helper.RestUtils;
import cz.cvut.nss.investmentmanagementsystem.model.Order;
import cz.cvut.nss.investmentmanagementsystem.model.dto.OrderDto;
import cz.cvut.nss.investmentmanagementsystem.model.dto.convertor.OrderConvertor;
import cz.cvut.nss.investmentmanagementsystem.model.enums.TransactionType;
import cz.cvut.nss.investmentmanagementsystem.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RestController
@RequestMapping("api/orders")
public class OrdersController {
    private final OrderService orderService;
    private final OrderConvertor orderConvertor;
    @Autowired
    public OrdersController(OrderService orderService, OrderConvertor orderConvertor) {
        this.orderService = orderService;
        this.orderConvertor = orderConvertor;
    }
    // create new order
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createOrder(@RequestBody OrderDto orderDto){
        Order order = orderConvertor.getEntity(orderDto);
        orderService.create(order);
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", order.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }
    // get order by id
    @GetMapping(value = "/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderDto> getOrder(@PathVariable Long orderId){
        Order order = orderService.get(orderId);
        OrderDto orderDto = orderConvertor.toDto(order);
        return ResponseEntity.ok(orderDto);
    }
    // update order
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateOrder(@RequestBody OrderDto orderDto){
        Order order = orderConvertor.getEntity(orderDto);
        orderService.update(order);
    }
    // delete order
    @DeleteMapping(value = "/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable Long orderId){
        orderService.delete(orderId);
    }
    // do smth order which accept smbd user
    @PostMapping(value = "/confirm")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void confirmOrder(@RequestParam Long orderAccepterId,
                                             @RequestParam Long orderId,
                                             @RequestParam Long portfolioOrderAccepterId,
                                             @RequestParam Long portfolioOrderCreatorId){
        orderService.confirmOrder(orderAccepterId, orderId, portfolioOrderAccepterId, portfolioOrderCreatorId);
    }
    // get all orders by sort
    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<OrderDto>> getOrders(@RequestParam(required = false) TransactionType transactionType,
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
        Page<Order> orders = orderService.findOrders(transactionType, quantity, price, dateCreatedOrder, marketDataSymbol, pageable);
        Page<OrderDto> orderDtoPage = orders.map(orderConvertor::toDto);
        return ResponseEntity.ok(orderDtoPage);
    }
}
