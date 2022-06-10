package com.ashkanzafari.assignment123.userservice.jwt;

import com.ashkanzafari.assignment123.userservice.config.security.RsaConfig;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;


/**
 * RsaKeyManager.
 *
 * <p>The Class to generate the public/private key for signing and verifying the JWT token</p>
 */
@Component
@RequiredArgsConstructor
public class RsaKeyManager implements InitializingBean {

  private final RsaConfig rsaConfig;

  private RSAPublicKey publicKey;
  private RSAPrivateKey privateKey;

  private RSAPublicKey readPublicKey(String key) throws Exception {
    String publicKeyPEM = key
            .replace("-----BEGIN PUBLIC KEY-----", "")
            .replaceAll(System.lineSeparator(), "")
            .replace("-----END PUBLIC KEY-----", "");
    byte[] encoded = Base64.decodeBase64(publicKeyPEM);
    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
    X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encoded);
    return (RSAPublicKey) keyFactory.generatePublic(keySpec);
  }

  private RSAPrivateKey readPrivateKey(String key) throws Exception {
    String privateKeyPEM = key
            .replace("-----BEGIN PRIVATE KEY-----", "")
            .replaceAll(System.lineSeparator(), "")
            .replace("-----END PRIVATE KEY-----", "");
    byte[] encoded = Base64.decodeBase64(privateKeyPEM);
    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
    PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
    return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
  }

  public RSAPublicKey getPublicKey() {
    return publicKey;
  }

  public RSAPrivateKey getPrivateKey() {
    return privateKey;
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    publicKey = readPublicKey(rsaConfig.getPublicKey());
    privateKey = readPrivateKey(rsaConfig.getPrivateKey());
    Assert.state(publicKey != null, "could not read the public key");
    Assert.state(privateKey != null, "could not read the private key");
  }
}
