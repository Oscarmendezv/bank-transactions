package com.oscarmendezv.banktransactions.web.model.input;

import javax.validation.constraints.NotBlank;

public class StatusRequest {

  @NotBlank
  private String reference;
  private String channel = "CLIENT";

  public String getReference() {
    return reference;
  }

  public void setReference(String reference) {
    this.reference = reference;
  }

  public String getChannel() {
    return channel;
  }

  public void setChannel(String channel) {
    this.channel = channel;
  }
}
