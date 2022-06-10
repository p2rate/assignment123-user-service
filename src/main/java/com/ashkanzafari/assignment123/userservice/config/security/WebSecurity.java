package com.ashkanzafari.assignment123.userservice.config.security;


import com.ashkanzafari.assignment123.userservice.jwt.JwtTokenVerifierFilter;
import com.ashkanzafari.assignment123.userservice.jwt.JwtUsernamePasswordAuthenticationFilter;
import com.ashkanzafari.assignment123.userservice.jwt.RsaKeyManager;
import com.ashkanzafari.assignment123.userservice.service.UserDetailsSerivceCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * WebSecurity.
 *
 * <p>Class to manage security configurations</p>
 */
@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurity extends WebSecurityConfigurerAdapter {

  private final UserDetailsSerivceCustom userDetailsService;
  private final PasswordEncoder passwordEncoder;
  private final RsaKeyManager rsaKeyManager;
  private final JwtConfig jwtConfig;

  private static final String HEADER_STRING = "Authorization";
  private static final String TOKEN_PREFIX = "Bearer ";

  private static final String[] AUTH_WHITELIST = {
          // -- Swagger UI v2
          "/v2/api-docs",
          "/swagger-resources",
          "/swagger-resources/**",
          "/configuration/ui",
          "/configuration/security",
          "/swagger-ui.html",
          "/webjars/**",
          // -- Swagger UI v3 (OpenAPI)
          "/v3/api-docs/**",
          "/swagger-ui/**"
          // other public endpoints of your API may be appended to this array
  };

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable()
            .authorizeRequests()
            .antMatchers(AUTH_WHITELIST).permitAll()
            .antMatchers("/api/pub/**").permitAll()
            .anyRequest()
            .authenticated().and()
            .addFilter(new JwtUsernamePasswordAuthenticationFilter(authenticationManager(),
                    jwtConfig.getExpirationInDays(), jwtConfig.getAesKey(), rsaKeyManager))
            .addFilter(new JwtTokenVerifierFilter(authenticationManager(), HEADER_STRING,
                    TOKEN_PREFIX, rsaKeyManager))
            // this disables session creation on Spring Security
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
  }

  @Override
  public void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
  }

}
