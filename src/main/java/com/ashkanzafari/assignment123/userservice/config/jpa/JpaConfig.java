package com.ashkanzafari.assignment123.userservice.config.jpa;

import com.ashkanzafari.assignment123.userservice.model.audit.AuditorAwareImpl;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * JpaConfig.
 *
 * <p>Class that is used to manage configuration of our JPA repository.</p>
 */
@Log4j2
@ToString
@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class JpaConfig {

    @Bean
    public AuditorAware<String> auditorAware() {
        return new AuditorAwareImpl();
    }

}
