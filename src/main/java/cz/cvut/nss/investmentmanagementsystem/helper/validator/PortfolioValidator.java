package cz.cvut.nss.investmentmanagementsystem.helper.validator;

import cz.cvut.nss.investmentmanagementsystem.model.Portfolio;
import cz.cvut.nss.investmentmanagementsystem.repository.PortfolioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PortfolioValidator extends AbstractValidator<Portfolio, Long>{
    @Autowired
    public PortfolioValidator(PortfolioRepository portfolioRepository){
        super(portfolioRepository);
    }
}
