package com.ashkanzafari.assignment123.userservice.config.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Data
@Configuration
@ConfigurationProperties(prefix = "app.sys.authentication", ignoreUnknownFields=true)
public class JwtConfig {

  private String header;

  private String prefix;

  private String aesKey;

  private long expirationInDays;

}
