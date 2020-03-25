package com.oscarmendezv.banktransactions.mapper.transactions;

import com.oscarmendezv.banktransactions.handler.exceptions.EntityNotFoundException;
import com.oscarmendezv.banktransactions.repository.AccountsRepository;
import com.oscarmendezv.banktransactions.repository.model.AccountEntity;
import com.oscarmendezv.banktransactions.repository.model.TransactionEntity;
import com.oscarmendezv.banktransactions.web.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class TransactionsMapper {

  @Autowired private AccountsRepository accountsRepository;
  @Autowired private Clock clock;

  public TransactionEntity mapToEntity(Transaction transaction) {
    return new TransactionEntity()
        .reference(transaction.getReference())
        .amount(transaction.getAmount())
        .date(Optional.ofNullable(transaction.getDate())
            .orElseGet(() -> LocalDateTime.now(clock)))
        .description(transaction.getDescription())
        .fee(transaction.getFee())
        .account(accountsRepository.findByIban(transaction.getIban())
            .map(account -> getFinalAccountBalance(transaction, account))
            .orElseThrow(() -> new EntityNotFoundException("Account with iban " + transaction.getIban() + " was not found.")));
  }

  public Transaction mapToOutput(TransactionEntity transactionEntity) {
    return new Transaction()
        .amount(transactionEntity.getAmount())
        .date(transactionEntity.getDate())
        .description(transactionEntity.getDescription())
        .fee(transactionEntity.getFee())
        .iban(transactionEntity.getAccount().getIban())
        .reference(transactionEntity.getReference());
  }

  private AccountEntity getFinalAccountBalance(Transaction transaction, AccountEntity account) {
    return account.balance(account.getBalance().add(transaction.getAmount()).subtract(transaction.getFee().abs()));
  }
}
