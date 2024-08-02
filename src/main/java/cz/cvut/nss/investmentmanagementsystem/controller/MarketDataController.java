package cz.cvut.nss.investmentmanagementsystem.controller;

import cz.cvut.nss.investmentmanagementsystem.helper.RestUtils;
import cz.cvut.nss.investmentmanagementsystem.model.MarketData;
import cz.cvut.nss.investmentmanagementsystem.model.dto.MarketDataDto;
import cz.cvut.nss.investmentmanagementsystem.model.dto.convertor.MarketDataConvertor;
import cz.cvut.nss.investmentmanagementsystem.service.MarketDataService;
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
@RequestMapping("api/market_data")
public class MarketDataController {
    private final MarketDataService marketDataService;

    @Autowired
    public MarketDataController(MarketDataService marketDataService) {
        this.marketDataService = marketDataService;
    }
    // create new market data
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createMarketData(@RequestBody MarketDataDto marketDataDto){
        MarketData marketData = MarketDataConvertor.getEntity(marketDataDto);
        marketDataService.create(marketData);
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", marketData.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }
    // get market data by id
    @GetMapping(value = "/{marketDataId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MarketDataDto> getMarketData(@PathVariable Long marketDataId){
        MarketData marketData = marketDataService.get(marketDataId);
        MarketDataDto marketDataDto = MarketDataConvertor.toDto(marketData);
        return ResponseEntity.ok(marketDataDto);
    }
    // update market data
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateMarketData(@RequestBody MarketData marketData){
        marketDataService.update(marketData);
    }
    // delete market data
    @DeleteMapping(value = "/{marketDataId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMarketData(@PathVariable Long marketDataId){
        marketDataService.delete(marketDataId);
    }
    // get market data by price between min price and max price
    @GetMapping(value = "/min-price/{minPrice}/max-price/{maxPrice}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MarketDataDto>> getAllMarketDataWithCurrentPriceBetween(@PathVariable BigDecimal minPrice
            , @PathVariable BigDecimal maxPrice){
        List<MarketDataDto> marketDataList = marketDataService.getAllMarketDataWithCurrentPriceBetween(minPrice, maxPrice).
                stream().map(MarketDataConvertor::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(marketDataList);
    }
}
