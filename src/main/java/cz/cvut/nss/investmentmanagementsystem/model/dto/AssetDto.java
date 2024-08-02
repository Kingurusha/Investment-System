package cz.cvut.nss.investmentmanagementsystem.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link cz.cvut.nss.investmentmanagementsystem.model.Asset}
 */
public record AssetDto(long id, BigDecimal quantity) implements Serializable {
}