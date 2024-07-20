package cz.cvut.nss.investmentmanagementsystem.controller;

import cz.cvut.nss.investmentmanagementsystem.helper.RestUtils;
import cz.cvut.nss.investmentmanagementsystem.model.Portfolio;
import cz.cvut.nss.investmentmanagementsystem.service.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/portfolios")
public class PortfoliosController {
    private final PortfolioService portfolioService;

    @Autowired
    public PortfoliosController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createPortfolio(@RequestBody Portfolio portfolio){
        portfolioService.create(portfolio);
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", portfolio.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }


}
