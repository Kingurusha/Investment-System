package cz.cvut.nss.investmentmanagementsystem.repository;

import cz.cvut.nss.investmentmanagementsystem.model.Asset;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface AssetRepository extends BaseRepository<Asset, Long> {
    Optional<Asset> findByPortfolioIdAndMarketDataSymbol(Long portfolioId, String marketDataSymbol);

    @Query("SELECT a FROM Asset a WHERE a.portfolio.user.id = :userId AND a.marketData.symbol = :marketDataSymbol")
    Optional<Asset> findByUserIdAndMarketDataSymbol(@Param("userId") Long userId, @Param("marketDataSymbol") String marketDataSymbol);

    List<Asset> findAllByPortfolioId(Long portfolioId);

    List<Asset> findAllByMarketDataSymbol(String symbol);

    List<Asset> findAllByQuantityGreaterThanEqual(BigDecimal minimumQuantity);
}
