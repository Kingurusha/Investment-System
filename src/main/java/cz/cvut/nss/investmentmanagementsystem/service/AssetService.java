package cz.cvut.nss.investmentmanagementsystem.service;

import cz.cvut.nss.investmentmanagementsystem.helper.validator.AssetValidator;
import cz.cvut.nss.investmentmanagementsystem.model.Asset;
import cz.cvut.nss.investmentmanagementsystem.repository.AssetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AssetService implements CrudService<Asset, Long> {
    private final AssetRepository assetRepository;
    private final AssetValidator assetValidator;
    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    @Autowired
    public AssetService(AssetRepository assetRepository, AssetValidator assetValidator) {
        this.assetRepository = assetRepository;
        this.assetValidator = assetValidator;
    }
    @Override
    @Transactional
    public void create(Asset asset){
        Long portfolioId = asset.getPortfolio().getId();
        String marketDataSymbol = asset.getMarketData().getSymbol();
        assetRepository.findByPortfolioIdAndMarketDataSymbol(portfolioId, marketDataSymbol)
                .ifPresentOrElse(existingAsset -> {
                    existingAsset.setQuantity(existingAsset.getQuantity().add(asset.getQuantity()));
                    assetRepository.save(existingAsset);
                }, () -> assetRepository.save(asset));
        LOG.debug("Create asset {}.", asset);
    }
    @Override
    @Transactional(readOnly = true)
    public Asset get(Long assetId){
        assetValidator.validateExistById(assetId);
        return assetRepository.findById(assetId).get();
    }
    @Override
    @Transactional
    public void update(Asset asset){
        assetValidator.validateExistById(asset.getId());
        assetRepository.save(asset);
        LOG.debug("Update asset {}.", asset);
    }
    @Override
    @Transactional
    public void delete(Long assetId){
        assetValidator.validateExistById(assetId);
        assetRepository.deleteById(assetId);
        LOG.debug("Delete asset with ID {}.", assetId);
    }
    @Transactional(readOnly = true)
    public List<Asset> getAllAssetsByPortfolioId(Long portfolioId) {
        return assetRepository.findAllByPortfolioId(portfolioId);
    }
    @Transactional(readOnly = true)
    public List<Asset> getAllAssetsByMarketDataSymbol(String symbol) {
        return assetRepository.findAllByMarketDataSymbol(symbol);
    }
    @Transactional(readOnly = true)
    public List<Asset> getAllAssetsWithMinimumQuantity(BigDecimal minimumQuantity) {
        return assetRepository.findAllByQuantityGreaterThanEqual(minimumQuantity);
    }
}
