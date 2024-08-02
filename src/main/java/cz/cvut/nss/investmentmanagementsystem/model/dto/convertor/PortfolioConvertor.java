package cz.cvut.nss.investmentmanagementsystem.model.dto.convertor;

import cz.cvut.nss.investmentmanagementsystem.model.Portfolio;
import cz.cvut.nss.investmentmanagementsystem.model.User;
import cz.cvut.nss.investmentmanagementsystem.model.dto.PortfolioDto;

public class PortfolioConvertor {
    public static PortfolioDto toDto(Portfolio portfolio) {
        PortfolioDto dto = new PortfolioDto(portfolio.getId(), portfolio.getName(), portfolio.getTotalValue(),
                portfolio.getUser().getId());
        return dto;
    }

    public static Portfolio getEntity(PortfolioDto dto, User user) {
        Portfolio portfolio = new Portfolio();
        portfolio.setId(dto.id());
        portfolio.setName(dto.name());
        portfolio.setTotalValue(dto.totalValue());
        portfolio.setUser(user);
        return portfolio;
    }
}
