package com.nghex.exe202.service;

import com.nghex.exe202.entity.Order;
import com.nghex.exe202.entity.Seller;
import com.nghex.exe202.entity.Transaction;

import java.util.List;

public interface TransactionService {
    Transaction createTransaction(Order order);
    List<Transaction> getTransactionBySeller(Seller seller);
    List<Transaction>getAllTransactions();
}
