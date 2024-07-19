package cz.cvut.nss.investmentmanagementsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import cz.cvut.nss.investmentmanagementsystem.model.enums.OrderStatus;
import cz.cvut.nss.investmentmanagementsystem.model.enums.TransactionType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "orders")
public class Order extends AbstractEntity implements Serializable {
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    @Column(nullable = false)
    private BigDecimal quantity;
    @Column(nullable = false)
    private BigDecimal price;
    @Column(nullable = false)
    private LocalDateTime dateCreatedOrder;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;
    @ManyToOne
    @JoinColumn(name = "market_data_id", nullable = false)
    @JsonIgnore
    private MarketData marketData;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset_id")
    @JsonIgnore
    private Asset asset;

}
