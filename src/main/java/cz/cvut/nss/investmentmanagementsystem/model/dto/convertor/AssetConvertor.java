package cz.cvut.nss.investmentmanagementsystem.model.dto.convertor;

import cz.cvut.nss.investmentmanagementsystem.model.Asset;
import cz.cvut.nss.investmentmanagementsystem.model.dto.AssetDto;

public class AssetConvertor {
    public static AssetDto toDto(Asset asset) {
        AssetDto dto = new AssetDto(asset.getId(), asset.getQuantity());
        return dto;
    }

    public static Asset getEntity(AssetDto dto) {
        Asset asset = new Asset();
        asset.setId(dto.id());
        asset.setQuantity(dto.quantity());
        return asset;
    }
}
