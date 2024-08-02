package cz.cvut.nss.investmentmanagementsystem.model.dto;

import cz.cvut.nss.investmentmanagementsystem.model.enums.InvestmentType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO for {@link cz.cvut.nss.investmentmanagementsystem.model.MarketData}
 */
public record MarketDataDto(long id, InvestmentType investmentType, String symbol, BigDecimal currentPrice,
                            LocalDateTime lastUpdateDate, BigDecimal lowPrice, BigDecimal highPrice,
                            BigDecimal volume) implements Serializable {
}