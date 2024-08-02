package cz.cvut.nss.investmentmanagementsystem.repository;

import cz.cvut.nss.investmentmanagementsystem.model.MarketData;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface MarketDataRepository extends BaseRepository<MarketData, Long> {
    List<MarketData> findAllByCurrentPriceBetween(BigDecimal startPrice, BigDecimal endPrice);
}
