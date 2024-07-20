package cz.cvut.nss.investmentmanagementsystem.service;

import cz.cvut.nss.investmentmanagementsystem.helper.validator.MarketDataValidator;
import cz.cvut.nss.investmentmanagementsystem.model.MarketData;
import cz.cvut.nss.investmentmanagementsystem.repository.MarketDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class MarketDataService implements CrudService<MarketData, Long>{
    private final MarketDataRepository marketDataRepository;
    private final MarketDataValidator marketDataValidator;

    @Autowired
    public MarketDataService(MarketDataRepository marketDataRepository, MarketDataValidator marketDataValidator) {
        this.marketDataRepository = marketDataRepository;
        this.marketDataValidator = marketDataValidator;
    }
    @Override
    @Transactional
    public void create(MarketData marketData){
        marketDataRepository.save(marketData);
    }
    @Override
    @Transactional(readOnly = true)
    public MarketData get(Long marketDataId){
        marketDataValidator.validateExistById(marketDataId);
        return marketDataRepository.findById(marketDataId).get();
    }
    @Override
    @Transactional
    public void update(MarketData marketData){
        marketDataValidator.validateExistById(marketData.getId());
        marketDataRepository.save(marketData);
    }
    @Override
    @Transactional
    public void delete(Long marketDataId){
        marketDataValidator.validateExistById(marketDataId);
        marketDataRepository.deleteById(marketDataId);
    }
}
