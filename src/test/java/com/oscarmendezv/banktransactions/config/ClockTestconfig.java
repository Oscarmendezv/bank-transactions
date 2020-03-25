package com.oscarmendezv.banktransactions.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

@Profile("integration")
@Configuration
public class ClockTestconfig {

  @Bean
  @Primary
  public Clock clock() {
    return Clock.fixed(Instant.parse("2020-03-25T16:55:42.000Z"), ZoneId.of("Europe/Madrid"));
  }
}
