package cz.cvut.nss.investmentmanagementsystem.service;

import cz.cvut.nss.investmentmanagementsystem.helper.validator.PortfolioValidator;
import cz.cvut.nss.investmentmanagementsystem.model.Asset;
import cz.cvut.nss.investmentmanagementsystem.model.Portfolio;
import cz.cvut.nss.investmentmanagementsystem.model.User;
import cz.cvut.nss.investmentmanagementsystem.model.enums.TransactionType;
import cz.cvut.nss.investmentmanagementsystem.repository.PortfolioRepository;
import cz.cvut.nss.investmentmanagementsystem.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Service
public class PortfolioService implements CrudService<Portfolio, Long>{
    private final PortfolioRepository portfolioRepository;
    private final PortfolioValidator portfolioValidator;
    private final UserRepository userRepository;
    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    public PortfolioService(PortfolioRepository portfolioRepository, PortfolioValidator portfolioValidator, UserRepository userRepository) {
        this.portfolioRepository = portfolioRepository;
        this.portfolioValidator = portfolioValidator;
        this.userRepository = userRepository;
    }
    @Override
    @Transactional
    public void create(Portfolio portfolio){
        portfolio.setTotalValue(BigDecimal.valueOf(0));
        portfolioRepository.save(portfolio);
        LOG.debug("Create portfolio {}.", portfolio);
    }
    @Override
    @Transactional(readOnly = true)
    public Portfolio get(Long portfolioId){
        portfolioValidator.validateExistById(portfolioId);
        return portfolioRepository.findById(portfolioId).get();
    }
    @Override
    @Transactional
    public void update(Portfolio portfolio){
        portfolioValidator.validateExistById(portfolio.getId());
        portfolioRepository.save(portfolio);
        LOG.debug("Update portfolio {}.", portfolio);
    }
    @Override
    @Transactional
    public void delete(Long portfolioId){
        portfolioValidator.validateExistById(portfolioId);
        portfolioRepository.deleteById(portfolioId);
        LOG.debug("Delete portfolio with ID {}.", portfolioId);
    }
    /**
     * Retrieves all assets in a portfolio.
     *
     * @param portfolioId the ID of the portfolio
     * @return the set of assets in the portfolio
     */
    @Transactional(readOnly = true)
    public Set<Asset> getAllAssetInPortfolio(Long portfolioId){
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new IllegalArgumentException("Portfolio not found with ID: " + portfolioId));
        return portfolio.getAssets();
    }

    /**
     * Rebalances the portfolio based on a new asset and transaction type
     *
     * @param portfolioId the ID of the portfolio
     * @param newAsset the new asset being added or removed
     * @param transactionType the type of transaction (sell or buy)
     */
    @Transactional
    public void rebalancePortfolio(Long portfolioId, Asset newAsset, TransactionType transactionType){
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new IllegalArgumentException("Portfolio not found with ID: " + portfolioId));
        BigDecimal assetPrice = newAsset.getQuantity().multiply(newAsset.getMarketData().getCurrentPrice());
        if (transactionType == TransactionType.BUY){
            portfolio.setTotalValue(portfolio.getTotalValue().add(assetPrice));
        } else if(transactionType == TransactionType.SELL){
            portfolio.setTotalValue(portfolio.getTotalValue().subtract(assetPrice));
        } else {
            throw new IllegalArgumentException("Unsupported transaction type: " + transactionType);
        }
        portfolioRepository.save(portfolio);
        LOG.debug("Rebalance value portfolio with ID {}.", portfolioId);
    }

    /**
     * Retrieves all portfolios for a user, ordered by total value in descending order.
     *
     * @param userId the ID of the user
     * @return the list of portfolios ordered by total value in descending order
     */
    @Transactional(readOnly = true)
    public List<Portfolio> getAllPortfoliosByUserIdOrderByTotalValueDesc(Long userId){
        return portfolioRepository.findAllByUserIdOrderByTotalValueDesc(userId);
    }

    /**
     * Retrieves all portfolios for a user, ordered by total value in ascending order.
     *
     * @param userId the ID of the user
     * @return the list of portfolios ordered by total value in ascending  order
     */
    @Transactional(readOnly = true)
    public List<Portfolio> getAllPortfoliosByUserIdOrderByTotalValueAsc(Long userId){
        return portfolioRepository.findAllByUserIdOrderByTotalValueAsc(userId);
    }
}
