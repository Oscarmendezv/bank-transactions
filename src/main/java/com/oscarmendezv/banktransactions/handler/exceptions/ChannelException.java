package com.oscarmendezv.banktransactions.handler.exceptions;

public class ChannelException extends RuntimeException {

  public ChannelException() {
    super("Channel not supported.");
  }
}
