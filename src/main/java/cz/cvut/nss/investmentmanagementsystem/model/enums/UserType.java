package cz.cvut.nss.investmentmanagementsystem.model.enums;

import io.swagger.v3.oas.annotations.media.Schema;

public enum UserType {
    @Schema(description = "System administrator with full access to all functionalities and settings")
    ADMIN,
    @Schema(description = "Regular user with a basic set of functions")
    BASIC_USER,
    @Schema(description = "Advanced user with extended functionalities")
    ADVANCED_USER,
    @Schema(description = "Guest user with limited access for system exploration")
    GUEST
}
