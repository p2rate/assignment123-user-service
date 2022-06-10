package com.ashkanzafari.assignment123.userservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "app.sys.config", ignoreUnknownFields=true)
public class RegistrationConfig {

  private int passwordExpiryInDays;
}
