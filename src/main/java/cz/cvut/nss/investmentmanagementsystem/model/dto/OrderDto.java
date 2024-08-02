package cz.cvut.nss.investmentmanagementsystem.model.dto;

import cz.cvut.nss.investmentmanagementsystem.model.enums.OrderStatus;
import cz.cvut.nss.investmentmanagementsystem.model.enums.TransactionType;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link cz.cvut.nss.investmentmanagementsystem.model.Order}
 */
public record OrderDto(long id, TransactionType transactionType, OrderStatus orderStatus, BigDecimal quantity,
                       BigDecimal price, long userId, long marketDataId, long assetId)
        implements Serializable {}
