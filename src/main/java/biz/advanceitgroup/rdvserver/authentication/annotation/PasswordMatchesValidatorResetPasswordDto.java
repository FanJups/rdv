package biz.advanceitgroup.rdvserver.authentication.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import biz.advanceitgroup.rdvserver.authentication.dto.ResetPasswordDto;

public class PasswordMatchesValidatorResetPasswordDto implements ConstraintValidator<PasswordMatchesResetPasswordDto, Object>{



	@Override
    public void initialize(final PasswordMatchesResetPasswordDto constraintAnnotation) {
        
    }

    @Override
    public boolean isValid(final Object obj, final ConstraintValidatorContext context) {
        
        final ResetPasswordDto resetPasswordDto = (ResetPasswordDto) obj;
        
       return resetPasswordDto.getPassword().equals(resetPasswordDto.getMatchingPassword());
        	
        
        
       
    }



}
