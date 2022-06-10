package com.ashkanzafari.assignment123.userservice.jwt;

import com.ashkanzafari.assignment123.userservice.dto.UserAuthenticationDto;
import com.ashkanzafari.assignment123.userservice.principal.UserPrincipal;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWEHeader;
import com.nimbusds.jose.crypto.RSAEncrypter;
import com.nimbusds.jwt.EncryptedJWT;
import com.nimbusds.jwt.JWTClaimsSet;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * JwtUsernamePasswordAuthenticationFilter.
 *
 * <p>The class which provides an authentication endpoint by implementing UsernamePasswordAuthenticationFilter.
 *
 * </p>
 */
public class JwtUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private AuthenticationManager authenticationManager;
  private long EXPIRATION_TIME;
  private String SECRET;
  private RsaKeyManager rsaKeyManager;

  public JwtUsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager,
                                                 long expiration, String secret, RsaKeyManager
                                                         rsaKeyManager) {
    this.authenticationManager = authenticationManager;
    this.EXPIRATION_TIME = expiration;
    this.SECRET = secret;
    this.rsaKeyManager = rsaKeyManager;

    setFilterProcessesUrl("/api/pub/v1/login");
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest req,
                                          HttpServletResponse res,
                                          FilterChain chain,
                                          Authentication auth) throws IOException {

    Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
    Set<String> authorityClaim = authorities.stream().map(authority -> authority.getAuthority()).collect(Collectors.toSet());
    Date now = new Date();
    String token;
    JWTClaimsSet jwtClaims = new JWTClaimsSet.Builder()
            .issuer("https://ashkanzafari.com")
            .subject(((UserPrincipal)auth.getPrincipal()).getUser().getId())
            .claim("authorities", authorityClaim)
            .expirationTime(java.sql.Date.valueOf(LocalDate.now()))
            .notBeforeTime(now)
            .issueTime(now)
            .jwtID(UUID.randomUUID().toString())
            .build();
    JWEHeader header = new JWEHeader(JWEAlgorithm.RSA_OAEP_256, EncryptionMethod.A128GCM);
    EncryptedJWT jwt = new EncryptedJWT(header, jwtClaims);
    RSAEncrypter encrypter = new RSAEncrypter(rsaKeyManager.getPublicKey());
    try {
      jwt.encrypt(encrypter);
      token = jwt.serialize();
      token = "Bearer " + token;
    } catch (Exception e) {
      token = "error_creating_token";
    }


    Map<String, String> responseBody = new HashMap<>();
    responseBody.put("token", token);

    res.addHeader("Authorization", token);
    res.setContentType("application/json");
    res.getWriter().write(new ObjectMapper().writeValueAsString(responseBody));
    res.getWriter().flush();
  }

  @Override
  protected void unsuccessfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            AuthenticationException failed)
          throws IOException, ServletException {

    Map<String, Object> responseBody = new HashMap<>();
    responseBody.put("errors", Arrays.asList("Username/Password is not valid"));
    responseBody.put("status", HttpStatus.UNAUTHORIZED.value());
    responseBody.put("message","unauhtorized");

    res.setStatus(HttpStatus.UNAUTHORIZED.value());
    res.setContentType("application/json");
    res.getWriter().write(new ObjectMapper().writeValueAsString(responseBody));

  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest req,
                                              HttpServletResponse res) throws AuthenticationException {
    try {
      UserAuthenticationDto creds = new ObjectMapper()
              .readValue(req.getInputStream(), UserAuthenticationDto.class);

      return authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(
                      creds.getUsername(),
                      creds.getPassword(),
                      new ArrayList<>())
      );
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}
