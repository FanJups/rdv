package biz.advanceitgroup.rdvserver.authentication.dto;



import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import biz.advanceitgroup.rdvserver.authentication.validation.PasswordMatches;
import biz.advanceitgroup.rdvserver.authentication.validation.ValidEmail;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 
 * @author Fanon Jupkwo
 *
 */

@Data
@AllArgsConstructor
@PasswordMatches
public class UserRegistrationDto {
	
	@NotNull
    @NotEmpty
	private String password;
	private String matchingPassword;
	
	
	@ValidEmail
	@NotNull
    @NotEmpty
	private String email;
	
	// The chosen role at registration
	
	private String registrationRole;
	
	
	
	

}
