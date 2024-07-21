package cz.cvut.nss.investmentmanagementsystem.specification;

import cz.cvut.nss.investmentmanagementsystem.model.Order;
import cz.cvut.nss.investmentmanagementsystem.model.enums.TransactionType;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderSpecification {
    public static Specification<Order> hasTransactionType(TransactionType transactionType) {
        return (root, query, builder) -> builder.equal(root.get("transactionType"), transactionType);
    }

    public static Specification<Order> hasQuantity(BigDecimal quantity) {
        return (root, query, builder) -> builder.equal(root.get("quantity"), quantity);
    }

    public static Specification<Order> hasPrice(BigDecimal price) {
        return (root, query, builder) -> builder.equal(root.get("price"), price);
    }

    public static Specification<Order> hasDateCreatedOrder(LocalDateTime dateCreatedOrder) {
        return (root, query, builder) -> builder.equal(root.get("dateCreatedOrder"), dateCreatedOrder);
    }

    public static Specification<Order> hasMarketDataSymbol(String symbol) {
        return (root, query, builder) -> builder.equal(root.get("marketData").get("symbol"), symbol);
    }
}
