package cz.cvut.nss.investmentmanagementsystem.service.factoryorder;

import cz.cvut.nss.investmentmanagementsystem.model.Order;
import cz.cvut.nss.investmentmanagementsystem.model.Portfolio;
import cz.cvut.nss.investmentmanagementsystem.model.User;

public interface TransactionProcessor {
    void process(Order newOrder, User orderAccepter, User orderCreator, Portfolio portfolioAccepter, Portfolio portfolioCreator);
}

