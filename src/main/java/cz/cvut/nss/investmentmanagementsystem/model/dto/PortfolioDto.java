package cz.cvut.nss.investmentmanagementsystem.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link cz.cvut.nss.investmentmanagementsystem.model.Portfolio}
 */
public record PortfolioDto(long id, String name, BigDecimal totalValue, long userId)
        implements Serializable {}