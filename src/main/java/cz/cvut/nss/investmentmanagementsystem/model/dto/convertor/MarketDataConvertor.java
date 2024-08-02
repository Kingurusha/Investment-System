package cz.cvut.nss.investmentmanagementsystem.model.dto.convertor;

import cz.cvut.nss.investmentmanagementsystem.model.MarketData;
import cz.cvut.nss.investmentmanagementsystem.model.dto.MarketDataDto;

public class MarketDataConvertor {
    public static MarketDataDto toDto(MarketData marketData) {
        return new MarketDataDto(
                marketData.getId(),
                marketData.getInvestmentType(),
                marketData.getSymbol(),
                marketData.getCurrentPrice(),
                marketData.getLastUpdateDate(),
                marketData.getLowPrice(),
                marketData.getHighPrice(),
                marketData.getVolume()
        );
    }

    public static MarketData getEntity(MarketDataDto marketDataDto) {
        MarketData marketData = new MarketData();
        marketData.setId(marketDataDto.id());
        marketData.setInvestmentType(marketDataDto.investmentType());
        marketData.setSymbol(marketDataDto.symbol());
        marketData.setCurrentPrice(marketDataDto.currentPrice());
        marketData.setLastUpdateDate(marketDataDto.lastUpdateDate());
        marketData.setLowPrice(marketDataDto.lowPrice());
        marketData.setHighPrice(marketDataDto.highPrice());
        marketData.setVolume(marketDataDto.volume());
        return marketData;
    }
}
