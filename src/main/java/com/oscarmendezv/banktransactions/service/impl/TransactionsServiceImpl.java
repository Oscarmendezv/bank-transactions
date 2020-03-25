package com.oscarmendezv.banktransactions.service.impl;

import com.oscarmendezv.banktransactions.handler.exceptions.CreationException;
import com.oscarmendezv.banktransactions.handler.exceptions.EntityNotFoundException;
import com.oscarmendezv.banktransactions.mapper.transactions.TransactionsMapper;
import com.oscarmendezv.banktransactions.repository.TransactionsRepository;
import com.oscarmendezv.banktransactions.repository.model.TransactionEntity;
import com.oscarmendezv.banktransactions.service.TransactionsService;
import com.oscarmendezv.banktransactions.strategy.status.StatusStrategy;
import com.oscarmendezv.banktransactions.strategy.status.StatusStrategyProvider;
import com.oscarmendezv.banktransactions.web.model.Transaction;
import com.oscarmendezv.banktransactions.web.model.output.StatusDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionsServiceImpl implements TransactionsService {

  @Autowired private TransactionsRepository transactionsRepository;
  @Autowired private TransactionsMapper mapper;
  @Autowired private StatusStrategyProvider strategyProvider;

  private static final String NOT_FOUND = "Transaction does not exist.";

  @Override
  @Transactional
  public Transaction createTransaction(Transaction transaction) {

    return Optional.of(transaction)
        .map(mapper::mapToEntity)
        .filter(entity -> !Optional.ofNullable(entity.getReference())
            .map(transactionsRepository::existsById)
            .orElse(false))
        .map(transactionsRepository::save)
        .map(mapper::mapToOutput)
        .orElseThrow(CreationException::new);
  }

  @Override
  public Transaction findTransactionById(String reference) {
    return transactionsRepository.findById(reference)
        .map(mapper::mapToOutput)
        .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND));
  }

  @Override
  public List<Transaction> findAllTransactions(String iban, String sortDirection) {
    Sort sortSpec = orderBy(sortDirection);

    return transactionsRepository.findAll(iban, sortSpec).stream()
        .map(mapper::mapToOutput)
        .collect(Collectors.toList());
  }

  @Override
  public StatusDTO getStatus(String reference, String channel) {
    return transactionsRepository.findById(reference)
        .map(transaction -> getStatusObject(transaction, channel))
        .orElseGet(() -> new StatusDTO()
            .reference(reference)
            .status("INVALID"));
  }

  private <T extends StatusStrategy> StatusDTO getStatusObject(TransactionEntity transactionEntity, String channel) {
    return strategyProvider
        .resolve(channel)
        .mapToStatusDTO(transactionEntity);
  }

  private Sort orderBy(String sortDirection) {
    return Optional.of(sortDirection)
        .filter(dir -> dir.equals("DESC"))
        .map(dir -> Sort.by(Sort.Direction.DESC, "amount"))
        .orElseGet(() -> Sort.by(Sort.Direction.ASC, "amount"));
  }
}
