package biz.advanceitgroup.rdvserver.authentication.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import biz.advanceitgroup.rdvserver.authentication.dto.UserRegistrationDto;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {
	
	@Override
    public void initialize(final PasswordMatches constraintAnnotation) {
        
    }

    @Override
    public boolean isValid(final Object obj, final ConstraintValidatorContext context) {
        final UserRegistrationDto user = (UserRegistrationDto) obj;
        return user.getPassword().equals(user.getMatchingPassword());
    }

}
