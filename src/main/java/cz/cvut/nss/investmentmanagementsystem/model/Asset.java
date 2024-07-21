package cz.cvut.nss.investmentmanagementsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Asset extends AbstractEntity implements Serializable {
    @Column(nullable = false)
    private BigDecimal quantity;

    @ManyToOne(optional = false)
    @JoinColumn(name = "portfolio_id", nullable = false)
    @JsonIgnore
    private Portfolio portfolio;
    @ManyToOne
    @JoinColumn(name = "market_data_id", nullable = false)
    private MarketData marketData;

    @OneToMany(mappedBy = "asset", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Order> orders;
}
