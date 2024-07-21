package cz.cvut.nss.investmentmanagementsystem.controller;

import cz.cvut.nss.investmentmanagementsystem.helper.RestUtils;
import cz.cvut.nss.investmentmanagementsystem.model.Asset;
import cz.cvut.nss.investmentmanagementsystem.model.Portfolio;
import cz.cvut.nss.investmentmanagementsystem.service.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("api/portfolios")
public class PortfoliosController {
    private final PortfolioService portfolioService;

    @Autowired
    public PortfoliosController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }
    // create new portfolio
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createPortfolio(@RequestBody Portfolio portfolio){
        portfolioService.create(portfolio);
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", portfolio.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }
    // get portfolio by id
    @GetMapping(value = "/{portfolioId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Portfolio> getPortfolioById(@PathVariable Long portfolioId){
        Portfolio portfolio = portfolioService.get(portfolioId);
        return ResponseEntity.ok(portfolio);
    }
    // update portfolio
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePortfolio(@RequestBody Portfolio portfolio){
        portfolioService.update(portfolio);
    }
    // delete portfolio
    @DeleteMapping(value = "/{portfolioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePortfolio(@PathVariable Long portfolioId){
        portfolioService.delete(portfolioId);
    }
    // get all asset in portfolio
    @GetMapping(value = "/{portfolioId}/assets", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<Asset>> getAllAssetInPortfolio(@PathVariable Long portfolioId){
        Set<Asset> assets = portfolioService.getAllAssetInPortfolio(portfolioId);
        return ResponseEntity.ok(assets);
    }
    // get portfolio by user and order by total value portfolio(desc)
    @GetMapping(value = "/user/{userId}/desc", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Portfolio>> getAllPortfoliosByUserIdOrderByTotalValueDesc(@PathVariable Long userId){
        List<Portfolio> portfolios = portfolioService.getAllPortfoliosByUserIdOrderByTotalValueDesc(userId);
        return ResponseEntity.ok(portfolios);
    }
    // get portfolio by user and order by total value portfolio(asc)
    @GetMapping(value = "/user/{userId}/asc", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Portfolio>> getAllPortfoliosByUserIdOrderByTotalValueAsc(@PathVariable Long userId){
        List<Portfolio> portfolios = portfolioService.getAllPortfoliosByUserIdOrderByTotalValueAsc(userId);
        return ResponseEntity.ok(portfolios);
    }
}
