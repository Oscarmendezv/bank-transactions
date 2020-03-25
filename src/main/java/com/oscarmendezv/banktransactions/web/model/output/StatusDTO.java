package com.oscarmendezv.banktransactions.web.model.output;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class StatusDTO {

  private String reference;
  private String status;
  private BigDecimal amount;
  private BigDecimal fee;

  public String getReference() {
    return reference;
  }

  public void setReference(String reference) {
    this.reference = reference;
  }

  public StatusDTO reference(String reference) {
    this.reference = reference;
    return this;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public StatusDTO status(String status) {
    this.status = status;
    return this;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public StatusDTO amount(BigDecimal amount) {
    this.amount = amount;
    return this;
  }

  public BigDecimal getFee() {
    return fee;
  }

  public void setFee(BigDecimal fee) {
    this.fee = fee;
  }

  public StatusDTO fee(BigDecimal fee) {
    this.fee = fee;
    return this;
  }
}
