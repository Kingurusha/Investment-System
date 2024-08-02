package cz.cvut.nss.investmentmanagementsystem.model;

import cz.cvut.nss.investmentmanagementsystem.model.enums.InvestmentType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class MarketData extends AbstractEntity implements Serializable {
    @Enumerated(EnumType.STRING)
    @Column(unique = true)
    private InvestmentType investmentType;
    @Column(nullable = false, unique = true)
    private String symbol;
    @Column(nullable = false)
    private BigDecimal currentPrice;
    @Column
    private LocalDateTime lastUpdateDate;
    @Column(nullable = false)
    private BigDecimal lowPrice;
    @Column(nullable = false)
    private BigDecimal highPrice;
    @Column
    private BigDecimal volume;
}
