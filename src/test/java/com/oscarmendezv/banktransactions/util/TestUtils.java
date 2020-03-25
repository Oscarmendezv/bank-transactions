package com.oscarmendezv.banktransactions.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oscarmendezv.banktransactions.util.config.ObjectMapperConfig;

import java.io.IOException;
import java.net.URL;

public class TestUtils {

  private static ObjectMapper mapper = new ObjectMapperConfig().objectMapper();

  public static <T> T loadObject(URL uri, Class<T> clazz) {
    try {
      return mapper.readValue(uri, clazz);
    } catch (IOException e) {
      throw new RuntimeException("Resource not available");
    }
  }
}
