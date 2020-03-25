package com.oscarmendezv.banktransactions.repository;

import com.oscarmendezv.banktransactions.repository.model.TransactionEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionsRepository extends JpaRepository<TransactionEntity, String> {

  @Query("SELECT t FROM TransactionEntity t WHERE " +
      "t.account.iban = :iban OR :iban is null")
  List<TransactionEntity> findAll(@Param("iban") String iban, Sort sort);
}
