package cz.cvut.nss.investmentmanagementsystem.controller;

import cz.cvut.nss.investmentmanagementsystem.helper.RestUtils;
import cz.cvut.nss.investmentmanagementsystem.model.Asset;
import cz.cvut.nss.investmentmanagementsystem.model.Portfolio;
import cz.cvut.nss.investmentmanagementsystem.model.dto.PortfolioDto;
import cz.cvut.nss.investmentmanagementsystem.model.dto.convertor.PortfolioConvertor;
import cz.cvut.nss.investmentmanagementsystem.service.PortfolioService;
import cz.cvut.nss.investmentmanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/portfolios")
public class PortfoliosController {
    private final PortfolioService portfolioService;
    private final UserService userService;

    @Autowired
    public PortfoliosController(PortfolioService portfolioService, UserService userService) {
        this.portfolioService = portfolioService;
        this.userService = userService;
    }
    // create new portfolio
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createPortfolio(@RequestBody PortfolioDto portfolioDto){
        Portfolio portfolio = PortfolioConvertor.getEntity(portfolioDto, userService.get(portfolioDto.userId()));
        portfolioService.create(portfolio);
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", portfolio.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }
    // get portfolio by id
    @GetMapping(value = "/{portfolioId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PortfolioDto> getPortfolioById(@PathVariable Long portfolioId){
        Portfolio portfolio = portfolioService.get(portfolioId);
        PortfolioDto portfolioDto = PortfolioConvertor.toDto(portfolio);
        return ResponseEntity.ok(portfolioDto);
    }
    // update portfolio
    @PutMapping(value = "/{portfolioId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePortfolio(@PathVariable Long portfolioId, @RequestBody PortfolioDto portfolioDto){
        Portfolio portfolio = PortfolioConvertor.getEntity(portfolioDto, userService.get(portfolioDto.userId()));
        portfolio.setId(portfolioId);
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
    public ResponseEntity<List<PortfolioDto>> getAllPortfoliosByUserIdOrderByTotalValueDesc(@PathVariable Long userId){
        List<PortfolioDto> portfolios = portfolioService.getAllPortfoliosByUserIdOrderByTotalValueDesc(userId).stream()
                .map(PortfolioConvertor::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(portfolios);
    }
    // get portfolio by user and order by total value portfolio(asc)
    @GetMapping(value = "/user/{userId}/asc", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PortfolioDto>> getAllPortfoliosByUserIdOrderByTotalValueAsc(@PathVariable Long userId){
        List<PortfolioDto> portfolios = portfolioService.getAllPortfoliosByUserIdOrderByTotalValueAsc(userId).stream()
                .map(PortfolioConvertor::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(portfolios);
    }
}
