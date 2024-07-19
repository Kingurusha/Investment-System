package cz.cvut.nss.investmentmanagementsystem.service;

import cz.cvut.nss.investmentmanagementsystem.helper.validator.AssetValidator;
import cz.cvut.nss.investmentmanagementsystem.model.Asset;
import cz.cvut.nss.investmentmanagementsystem.repository.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AssetService implements CrudService<Asset, Long> {
    private final AssetRepository assetRepository;
    private final AssetValidator assetValidator;

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
    }
    @Override
    @Transactional
    public void delete(Long assetId){
        assetValidator.validateExistById(assetId);
        assetRepository.deleteById(assetId);
    }

}
