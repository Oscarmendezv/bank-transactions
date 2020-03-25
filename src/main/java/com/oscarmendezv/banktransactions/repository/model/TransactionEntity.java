package com.oscarmendezv.banktransactions.repository.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transaction")
public class TransactionEntity {

  @Id
  @Column(unique = true)
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String reference;

  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  private LocalDateTime date;
  private BigDecimal amount;
  private BigDecimal fee;
  private String description;

  @ManyToOne
  @JoinColumn(name = "account_id")
  private AccountEntity account;

  public String getReference() {
    return reference;
  }

  public TransactionEntity reference(String reference) {
    this.reference = reference;
    return this;
  }

  public LocalDateTime getDate() {
    return date;
  }

  public TransactionEntity date(LocalDateTime date) {
    this.date = date;
    return this;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public TransactionEntity amount(BigDecimal amount) {
    this.amount = amount;
    return this;
  }

  public BigDecimal getFee() {
    return fee;
  }

  public TransactionEntity fee(BigDecimal fee) {
    this.fee = fee;
    return this;
  }

  public String getDescription() {
    return description;
  }

  public TransactionEntity description(String description) {
    this.description = description;
    return this;
  }

  public AccountEntity getAccount() {
    return account;
  }

  public TransactionEntity account(AccountEntity account) {
    this.account = account;
    return this;
  }
}
