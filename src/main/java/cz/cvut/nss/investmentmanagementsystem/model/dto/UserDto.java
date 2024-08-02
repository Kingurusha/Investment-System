package cz.cvut.nss.investmentmanagementsystem.model.dto;

import cz.cvut.nss.investmentmanagementsystem.model.enums.UserType;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link cz.cvut.nss.investmentmanagementsystem.model.User}
 */
public record UserDto(long id, String username,String password, String email, BigDecimal balance, UserType userType)
        implements Serializable {}
