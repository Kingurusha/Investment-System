package cz.cvut.nss.investmentmanagementsystem.repository;

import cz.cvut.nss.investmentmanagementsystem.model.MarketData;
import org.springframework.stereotype.Repository;

@Repository
public interface MarketDataRepository extends BaseRepository<MarketData, Long> {
}
