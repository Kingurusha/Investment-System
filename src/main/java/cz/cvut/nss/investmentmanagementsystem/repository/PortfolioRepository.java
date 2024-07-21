package cz.cvut.nss.investmentmanagementsystem.repository;

import cz.cvut.nss.investmentmanagementsystem.model.Portfolio;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PortfolioRepository extends BaseRepository<Portfolio, Long> {
    List<Portfolio> findAllByUserId(Long userId);
    @Query("SELECT p from Portfolio p where p.user.id = :userId ORDER BY p.totalValue DESC")
    List<Portfolio> findAllByUserIdOrderByTotalValueDesc(Long userId);
    @Query("SELECT p from Portfolio p where p.user.id = :userId ORDER BY p.totalValue ASC")
    List<Portfolio> findAllByUserIdOrderByTotalValueAsc(Long userId);
}
