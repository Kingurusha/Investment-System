package cz.cvut.nss.investmentmanagementsystem.helper.validator;

import cz.cvut.nss.investmentmanagementsystem.model.Asset;
import cz.cvut.nss.investmentmanagementsystem.repository.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AssetValidator extends AbstractValidator<Asset, Long>{
    @Autowired
    public AssetValidator(AssetRepository assetRepository) {
        super(assetRepository);
    }
}
