package cz.cvut.nss.investmentmanagementsystem.controller;

import cz.cvut.nss.investmentmanagementsystem.helper.RestUtils;
import cz.cvut.nss.investmentmanagementsystem.model.Asset;
import cz.cvut.nss.investmentmanagementsystem.model.dto.AssetDto;
import cz.cvut.nss.investmentmanagementsystem.model.dto.convertor.AssetConvertor;
import cz.cvut.nss.investmentmanagementsystem.service.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/assets")
public class AssetsController {
    private final AssetService assetService;

    @Autowired
    public AssetsController(AssetService assetService) {
        this.assetService = assetService;
    }
    // create new asset
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createAsset(@RequestBody AssetDto assetDto){
        Asset asset = AssetConvertor.getEntity(assetDto);
        assetService.create(asset);
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", asset.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }
    // get asset by id
    @GetMapping(value = "/{assetId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AssetDto> getAsset(@PathVariable Long assetId){
        Asset asset = assetService.get(assetId);
        AssetDto assetDto = AssetConvertor.toDto(asset);
        return ResponseEntity.ok(assetDto);
    }
    // update asset
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAsset(@RequestBody AssetDto assetDto){
        Asset asset = AssetConvertor.getEntity(assetDto);
        assetService.update(asset);
    }
    // delete asset
    @DeleteMapping(value = "/{assetId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAsset(@PathVariable Long assetId){
        assetService.delete(assetId);
    }
    // get all asset by portfolio id
    @GetMapping(value = "/portfolio/{portfolioId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AssetDto>> getAllAssetsByPortfolioId(@PathVariable Long portfolioId){
        return ResponseEntity.ok(assetService.getAllAssetsByPortfolioId(portfolioId).
                stream().map(AssetConvertor::toDto).collect(Collectors.toList()));
    }
    // get all asset by market data symbol
    @GetMapping(value = "/market-data/{marketDataSymbol}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AssetDto>> getAllAssetsByMarketDataSymbol(@PathVariable String marketDataSymbol){
        return ResponseEntity.ok(assetService.getAllAssetsByMarketDataSymbol(marketDataSymbol).
                stream().map(AssetConvertor::toDto).collect(Collectors.toList()));
    }
    // get all asset by minimal quantity
    @GetMapping(value = "/quantity/{minimumQuantity}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AssetDto>> getAllAssetsWithMinimumQuantity(@PathVariable BigDecimal minimumQuantity){
        return ResponseEntity.ok(assetService.getAllAssetsWithMinimumQuantity(minimumQuantity).
                stream().map(AssetConvertor::toDto).collect(Collectors.toList()));
    }
}
