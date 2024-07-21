package cz.cvut.nss.investmentmanagementsystem.model.enums;

import io.swagger.v3.oas.annotations.media.Schema;

public enum TransactionType {
    @Schema(description = "Transaction to purchase")
    BUY,
    @Schema(description = "Transaction for sale")
    SELL
}
