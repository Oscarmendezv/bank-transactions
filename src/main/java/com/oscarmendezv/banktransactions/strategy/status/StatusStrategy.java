package com.oscarmendezv.banktransactions.strategy.status;

import com.oscarmendezv.banktransactions.repository.model.TransactionEntity;
import com.oscarmendezv.banktransactions.web.model.output.StatusDTO;

public interface StatusStrategy {

  StatusDTO mapToStatusDTO(TransactionEntity entity);
}
