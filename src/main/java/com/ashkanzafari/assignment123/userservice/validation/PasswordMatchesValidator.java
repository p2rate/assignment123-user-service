package com.ashkanzafari.assignment123.userservice.validation;


import com.ashkanzafari.assignment123.userservice.dto.request.UserRegisterationRequestDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * PasswordMatchesValidator.
 *
 * <p></p>
 */
public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

  @Override
  public boolean isValid(Object obj, ConstraintValidatorContext context) {
    UserRegisterationRequestDto user = (UserRegisterationRequestDto) obj;
    if (user.getPassword() == null | user.getPasswordConfirm() == null) {
      if (user.getPassword() == user.getPasswordConfirm())
        return true;
      else
        return false;
    }
    return user.getPassword().equals(user.getPasswordConfirm());

  }
}
