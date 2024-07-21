package cz.cvut.nss.investmentmanagementsystem.controller;

import cz.cvut.nss.investmentmanagementsystem.helper.RestUtils;
import cz.cvut.nss.investmentmanagementsystem.model.MarketData;
import cz.cvut.nss.investmentmanagementsystem.service.MarketDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Void> createMarketData(@RequestBody MarketData marketData){
        marketDataService.create(marketData);
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", marketData.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }
    // get market data by id
    @GetMapping(value = "/{marketDataId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MarketData> getMarketData(@PathVariable Long marketDataId){
        MarketData marketData = marketDataService.get(marketDataId);
        return ResponseEntity.ok(marketData);
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
}
