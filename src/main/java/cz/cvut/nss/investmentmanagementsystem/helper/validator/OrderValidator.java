package cz.cvut.nss.investmentmanagementsystem.helper.validator;

import cz.cvut.nss.investmentmanagementsystem.model.Order;
import cz.cvut.nss.investmentmanagementsystem.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderValidator extends AbstractValidator<Order, Long>{
    @Autowired
    public OrderValidator(OrderRepository orderRepository) {
        super(orderRepository);
    }
}
