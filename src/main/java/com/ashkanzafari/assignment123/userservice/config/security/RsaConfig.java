package com.ashkanzafari.assignment123.userservice.config.security;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "app.sys.authentication", ignoreUnknownFields=true)
public class RsaConfig {

  @Value("${app.sys.authentication.publicKey}")
  private String publicKey;
  @Value("${app.sys.authentication.privateKey}")
  private String privateKey;

}
