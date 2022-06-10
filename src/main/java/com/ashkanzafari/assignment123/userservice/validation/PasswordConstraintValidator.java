package com.ashkanzafari.assignment123.userservice.validation;

import org.passay.DigitCharacterRule;
import org.passay.LengthRule;
import org.passay.LowercaseCharacterRule;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.RuleResult;
import org.passay.SpecialCharacterRule;
import org.passay.UppercaseCharacterRule;
import org.passay.WhitespaceRule;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

/**
 * PasswordConstraintValidator.
 *
 * <p></p>
 */
public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {
  @Override
  public void initialize(ValidPassword arg0) {
  }

  @Override
  public boolean isValid(String password, ConstraintValidatorContext context) {

    if (password == null)
      return false;
    PasswordValidator validator = new PasswordValidator(Arrays.asList(
            new LengthRule(8, 30),
            new LowercaseCharacterRule(1),
            new UppercaseCharacterRule(1),
            new DigitCharacterRule(1),
            new SpecialCharacterRule(1),
//           new NumericalSequenceRule(3,false),
//           new AlphabeticalSequenceRule(3,false),
//           new QwertySequenceRule(3,false),
            new WhitespaceRule()));

    RuleResult result = validator.validate(new PasswordData(password));
    if (result.isValid()) {
      return true;
    }

    return false;
  }
}
