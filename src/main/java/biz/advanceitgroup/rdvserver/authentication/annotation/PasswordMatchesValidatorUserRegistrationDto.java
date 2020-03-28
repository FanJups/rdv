package biz.advanceitgroup.rdvserver.authentication.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


import biz.advanceitgroup.rdvserver.authentication.dto.UserRegistrationDto;

public class PasswordMatchesValidatorUserRegistrationDto implements ConstraintValidator<PasswordMatchesUserRegistrationDto, Object> {
	
	@Override
    public void initialize(final PasswordMatchesUserRegistrationDto constraintAnnotation) {
        
    }

    @Override
    public boolean isValid(final Object obj, final ConstraintValidatorContext context) {
    	
        final UserRegistrationDto userRegistrationDto = (UserRegistrationDto) obj;
      
       return userRegistrationDto.getPassword().equals(userRegistrationDto.getMatchingPassword());
        	
     
        
    }

}
