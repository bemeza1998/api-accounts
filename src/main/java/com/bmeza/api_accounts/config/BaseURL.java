package com.bmeza.api_accounts.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;


@Getter
@Component
public class BaseURL {
    
  private final String apiClientsURL;

  public BaseURL(@Value("${api-clients.base-url}") String apiClientsURL) {
    this.apiClientsURL = apiClientsURL;
  }
}
