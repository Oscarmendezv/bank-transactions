package com.oscarmendezv.banktransactions.repository.model;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "account")
public class AccountEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @Min(value = 0, message = "Transaction could not be performed because it would result on negative balance.")
  private BigDecimal balance;

  @Column(name = "account_iban")
  private String iban;

  @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
  private List<TransactionEntity> transactions;

  public BigDecimal getBalance() {
    return balance;
  }

  public String getIban() {
    return iban;
   }

  public AccountEntity balance(BigDecimal balance) {
    this.balance = balance;
    return this;
  }
}
