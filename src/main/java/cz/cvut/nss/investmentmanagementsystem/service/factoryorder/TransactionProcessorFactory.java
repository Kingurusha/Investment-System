package cz.cvut.nss.investmentmanagementsystem.service.factoryorder;

import cz.cvut.nss.investmentmanagementsystem.model.enums.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionProcessorFactory {
    private final BuyTransactionProcessor buyTransactionProcessor;
    private final SellTransactionProcessor sellTransactionProcessor;
    @Autowired
    public TransactionProcessorFactory(BuyTransactionProcessor buyTransactionProcessor, SellTransactionProcessor sellTransactionProcessor) {
        this.buyTransactionProcessor = buyTransactionProcessor;
        this.sellTransactionProcessor = sellTransactionProcessor;
    }
    public TransactionProcessor getProcessor(TransactionType transactionType){
        switch (transactionType){
            case BUY -> {
                return buyTransactionProcessor;
            } case SELL -> {
                return sellTransactionProcessor;
            }default -> {
                throw new IllegalArgumentException("Unsupported transaction type: " + transactionType);
            }
        }
    }
}
