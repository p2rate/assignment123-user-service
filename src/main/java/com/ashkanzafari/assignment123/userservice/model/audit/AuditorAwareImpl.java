package com.ashkanzafari.assignment123.userservice.model.audit;

import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

/**
 * AuditorAwareImpl.
 *
 * <p>Class that is used to ensure that we can automatically populate last modified user.</p>
 */
@Log4j2
@ToString
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {

      String userId = "";
      try{
        userId = SecurityContextHolder.getContext().getAuthentication().getName();
      } catch (Exception e){
      }

      return Optional.of(userId);
    }

}
