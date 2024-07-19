package cz.cvut.nss.investmentmanagementsystem.helper.validator;

import cz.cvut.nss.investmentmanagementsystem.model.MarketData;
import cz.cvut.nss.investmentmanagementsystem.repository.MarketDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MarketDataValidator extends AbstractValidator<MarketData, Long>{
    @Autowired
    public MarketDataValidator(MarketDataRepository marketDataRepository) {
        super(marketDataRepository);
    }
}
