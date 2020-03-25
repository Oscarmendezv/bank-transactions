package com.oscarmendezv.banktransactions.strategy.status.impl;

import com.oscarmendezv.banktransactions.strategy.status.StatusStrategy;
import com.oscarmendezv.banktransactions.repository.model.TransactionEntity;
import com.oscarmendezv.banktransactions.strategy.status.StatusVersion;
import com.oscarmendezv.banktransactions.strategy.status.constants.StatusConstants;
import com.oscarmendezv.banktransactions.web.model.output.StatusDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDate;

@Component
@StatusVersion(type = "INTERNAL")
public class InternalStatusStrategy implements StatusStrategy {

  @Autowired private Clock clock;

  @Override
  public StatusDTO mapToStatusDTO(TransactionEntity entity) {
    String status;
    LocalDate date = entity.getDate().toLocalDate();

    if(date.isBefore(LocalDate.now(clock))) {
      status = StatusConstants.SETTLED;
    } else if(date.isEqual(LocalDate.now())) {
      status = StatusConstants.PENDING;
    } else {
      status = StatusConstants.FUTURE;
    }

    return new StatusDTO()
        .reference(entity.getReference())
        .status(status)
        .amount(entity.getAmount())
        .fee(entity.getFee());
  }
}
