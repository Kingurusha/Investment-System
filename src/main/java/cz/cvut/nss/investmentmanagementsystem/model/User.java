package cz.cvut.nss.investmentmanagementsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import cz.cvut.nss.investmentmanagementsystem.model.enums.UserType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "users")
public class User extends AbstractEntity implements Serializable {
    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    @JsonIgnore
    private String password;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private BigDecimal balance;
    @Enumerated(EnumType.STRING)
    private UserType userType;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Portfolio> portfolios;
}
