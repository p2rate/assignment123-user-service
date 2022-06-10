package com.ashkanzafari.assignment123.userservice.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * EmailValidator.
 *
 * <p></p>
 */
public class EmailValidator implements ConstraintValidator<ValidEmail, String> {

  private Pattern pattern;
  private Matcher matcher;
  private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@"
          + "[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$";


  private boolean validateEmail(String email) {

    if (email == null || email.equals(""))
      return true;
    pattern = Pattern.compile(EMAIL_PATTERN);
    matcher = pattern.matcher(email);
    return matcher.matches();
  }

  @Override
  public void initialize(ValidEmail constraintAnnotation) {
  }

  @Override
  public boolean isValid(String email, ConstraintValidatorContext context) {
    return (validateEmail(email));
  }
}
