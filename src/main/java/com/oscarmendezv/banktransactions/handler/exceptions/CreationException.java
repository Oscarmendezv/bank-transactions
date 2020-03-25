package com.oscarmendezv.banktransactions.handler.exceptions;

public class CreationException extends RuntimeException {
  public CreationException() {
    super("Error inserting in the DB. Transaction already exists.");
  }
}
