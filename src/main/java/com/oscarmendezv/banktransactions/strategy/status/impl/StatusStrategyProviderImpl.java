package com.oscarmendezv.banktransactions.strategy.status.impl;

import com.oscarmendezv.banktransactions.handler.exceptions.ChannelException;
import com.oscarmendezv.banktransactions.strategy.status.StatusStrategy;
import com.oscarmendezv.banktransactions.strategy.status.StatusStrategyProvider;
import com.oscarmendezv.banktransactions.strategy.status.StatusVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StatusStrategyProviderImpl implements StatusStrategyProvider {

  @Autowired private List<StatusStrategy> strategies;


  @Override
  public StatusStrategy resolve(String channel) {
    return strategies.stream()
        .filter(strategy -> strategy.getClass().getAnnotation(StatusVersion.class).type().equals(channel.toUpperCase()))
        .findFirst()
        .orElseThrow(ChannelException::new);
  }
}
