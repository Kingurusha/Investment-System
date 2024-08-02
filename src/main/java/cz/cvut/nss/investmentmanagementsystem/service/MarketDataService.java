package cz.cvut.nss.investmentmanagementsystem.service;

import cz.cvut.nss.investmentmanagementsystem.helper.validator.MarketDataValidator;
import cz.cvut.nss.investmentmanagementsystem.model.MarketData;
import cz.cvut.nss.investmentmanagementsystem.repository.MarketDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MarketDataService implements CrudService<MarketData, Long>{
    private final MarketDataRepository marketDataRepository;
    private final MarketDataValidator marketDataValidator;
    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    @Autowired
    public MarketDataService(MarketDataRepository marketDataRepository, MarketDataValidator marketDataValidator) {
        this.marketDataRepository = marketDataRepository;
        this.marketDataValidator = marketDataValidator;
    }
    @Override
    @Transactional
    public void create(MarketData marketData){
        marketDataRepository.save(marketData);
        LOG.debug("Create market data {}.", marketData);
    }
    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "marketDataCache", key = "#marketDataId")
    public MarketData get(Long marketDataId){
        marketDataValidator.validateExistById(marketDataId);
        return marketDataRepository.findById(marketDataId).get();
    }
    @Override
    @Transactional
    public void update(MarketData marketData){
        marketDataValidator.validateExistById(marketData.getId());
        marketData.setLastUpdateDate(LocalDateTime.now());
        marketDataRepository.save(marketData);
        LOG.debug("Update market data {}.", marketData);
    }
    @Override
    @Transactional
    public void delete(Long marketDataId){
        marketDataValidator.validateExistById(marketDataId);
        marketDataRepository.deleteById(marketDataId);
        LOG.debug("Delete market data with ID {}.", marketDataId);
    }

    /**
     * Finds all market data entities with current price between specified minPrice and maxPrice.
     *
     * @param minPrice the min price
     * @param maxPrice the max price
     * @return a list of market data entities
     */
    @Transactional(readOnly = true)
    public List<MarketData> getAllMarketDataWithCurrentPriceBetween(BigDecimal minPrice, BigDecimal maxPrice){
        return marketDataRepository.findAllByCurrentPriceBetween(minPrice, maxPrice);
    }
}
