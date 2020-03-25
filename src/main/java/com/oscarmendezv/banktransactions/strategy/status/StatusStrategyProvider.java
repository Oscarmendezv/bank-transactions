package com.oscarmendezv.banktransactions.strategy.status;

public interface StatusStrategyProvider {

  StatusStrategy resolve(String channel);
}
