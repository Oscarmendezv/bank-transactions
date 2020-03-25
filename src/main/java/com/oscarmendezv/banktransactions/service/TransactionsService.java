package com.oscarmendezv.banktransactions.service;

import com.oscarmendezv.banktransactions.web.model.output.StatusDTO;
import com.oscarmendezv.banktransactions.web.model.Transaction;

import java.util.List;

public interface TransactionsService {

  Transaction createTransaction(Transaction transaction);

  Transaction findTransactionById(String reference);

  List<Transaction> findAllTransactions(String iban, String sortDirection);

  StatusDTO getStatus(String reference, String channel);
}
