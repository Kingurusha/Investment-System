package cz.cvut.nss.investmentmanagementsystem.model.enums;

import io.swagger.v3.oas.annotations.media.Schema;

public enum OrderStatus {
    @Schema(description = "The order has been placed by the user and is awaiting a willing user")
    OPEN,
    @Schema(description = "The order executed and funds credited")
    EXECUTED,
    @Schema(description = "The order is canceled for any reason")
    CANCELLED
}
