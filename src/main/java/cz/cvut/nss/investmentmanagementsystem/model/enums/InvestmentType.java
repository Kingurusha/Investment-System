package cz.cvut.nss.investmentmanagementsystem.model.enums;
import io.swagger.v3.oas.annotations.media.Schema;

public enum InvestmentType {
    @Schema(description = "Shares of ownership in a corporation")
    STOCKS,
    @Schema(description = "Debt securities issued by corporations or governments")
    BONDS,
    @Schema(description = "Digital or virtual currencies like Bitcoin and Ethereum")
    CRYPTOCURRENCY,
    @Schema(description = "Investments in residential, commercial, or industrial properties")
    REAL_ESTATE,
    @Schema(description = "Investment funds that pool money from many investors to purchase securities")
    MUTUAL_FUNDS,
    @Schema(description = "Exchange-Traded Funds, which are traded on stock exchanges and" +
            " hold assets like stocks, commodities, or bonds")
    ETFs,
    @Schema(description = "Physical goods such as gold, oil, and agricultural products")
    COMMODITIES,
    @Schema(description = "Financial derivatives that give the right, but not the obligation," +
            " to buy or sell an asset at a predetermined price")
    OPTIONS,
    @Schema(description = "Financial contracts obligating the buyer to purchase an asset or" +
            " the seller to sell an asset at a predetermined future date and price")
    FUTURES,
    @Schema(description = "The foreign exchange market for trading currencies")
    FOREX,
    @Schema(description = "Investments in private companies not listed on public exchanges")
    PRIVATE_EQUITY,
    @Schema(description = "Funding provided to startups and small businesses with high growth potential")
    VENTURE_CAPITAL,
    @Schema(description = "Private investment funds that use various strategies to earn" +
            " active returns for their investors")
    HEDGE_FUNDS,
    @Schema(description = "Real Estate Investment Trusts, companies that own, operate, or" +
            " finance income-generating real estate")
    REITs,
    @Schema(description = "Physical items like art, antiques, and" +
            " rare items that can be collected and potentially increase in value")
    COLLECTIBLES,
    @Schema(description = "Bank accounts that earn interest on the deposited funds")
    SAVINGS_ACCOUNT,
    @Schema(description = "Bank-issued certificates that pay interest over a fixed term")
    CERTIFICATES_OF_DEPOSIT,
    @Schema(description = "Government-issued debt securities, such as Treasury bonds and bills")
    TREASURIES
}
