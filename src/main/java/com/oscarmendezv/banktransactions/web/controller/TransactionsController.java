package com.oscarmendezv.banktransactions.web.controller;

import com.oscarmendezv.banktransactions.web.model.output.StatusDTO;
import com.oscarmendezv.banktransactions.web.model.input.StatusRequest;
import com.oscarmendezv.banktransactions.web.model.Transaction;
import com.oscarmendezv.banktransactions.service.TransactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "transactions")
public class TransactionsController {

  @Autowired private TransactionsService transactionsService;

  @GetMapping
  public ResponseEntity<List<Transaction>> findAllTransactions(@RequestParam(value = "account_iban", required = false) String iban,
                                                               @RequestParam(value = "sort", defaultValue = "DESC")
                                                             String sortDirection) {

    return ResponseEntity.ok(transactionsService.findAllTransactions(iban, sortDirection));
  }

  @GetMapping(path = "/{reference}")
  public ResponseEntity<Transaction> findTransactionDetails(@PathVariable("reference") String reference) {

    return ResponseEntity.ok(transactionsService.findTransactionById(reference));
  }

  @PostMapping
  public ResponseEntity<Object> createTransaction(@RequestBody @Valid Transaction transaction) {

    Transaction transactionDTO = transactionsService.createTransaction(transaction);

    URI location = ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{reference}")
        .buildAndExpand(transactionDTO.getReference())
        .toUri();

    return ResponseEntity.created(location).build();
  }

  @PostMapping("/status")
  public ResponseEntity<StatusDTO> getTransactionStatus(@RequestBody @Valid StatusRequest statusRequest) {

    return ResponseEntity.ok(transactionsService.getStatus(statusRequest.getReference(), statusRequest.getChannel()));
  }
}
