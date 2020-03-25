package com.oscarmendezv.banktransactions.web.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {

  @JsonProperty("reference")
  private String reference;

  @NotBlank
  @JsonProperty("account_iban")
  private String iban;

  @JsonProperty("date")
  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  private LocalDateTime date;

  @NotNull
  @JsonProperty("amount")
  private BigDecimal amount;

  @JsonProperty("fee")
  private BigDecimal fee;

  @JsonProperty("description")
  private String description;

  public String getReference() {
    return reference;
  }

  public void setReference(String reference) {
    this.reference = reference;
  }

  public Transaction reference(String reference) {
    this.reference = reference;
    return this;
  }

  public LocalDateTime getDate() {
    return date;
  }

  public void setDate(LocalDateTime date) {
    this.date = date;
  }

  public Transaction date(LocalDateTime date) {
    this.date = date;
    return this;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public Transaction amount(BigDecimal amount) {
    this.amount = amount;
    return this;
  }

  public BigDecimal getFee() {
    return fee;
  }

  public void setFee(BigDecimal fee) {
    this.fee = fee;
  }

  public Transaction fee(BigDecimal fee) {
    this.fee = fee;
    return this;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Transaction description(String description) {
    this.description = description;
    return this;
  }

  public String getIban() {
    return iban;
  }

  public void setIban(String iban) {
    this.iban = iban;
  }

  public Transaction iban(String iban) {
    this.iban = iban;
    return this;
  }
}
