package biz.advanceitgroup.rdvserver.authentication.dto;



import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import biz.advanceitgroup.rdvserver.authentication.annotation.PasswordMatchesUserRegistrationDto;
import biz.advanceitgroup.rdvserver.authentication.annotation.ValidEmail;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 
 * @author Fanon Jupkwo
 *
 */

@Data
@AllArgsConstructor 
@PasswordMatchesUserRegistrationDto
public class UserRegistrationDto {
	
	@NotNull
    @NotEmpty
	private String password;
	
	@NotNull
    @NotEmpty
	private String matchingPassword;
	
	
	@ValidEmail
	@NotNull
    @NotEmpty
	private String email;
	
	// The chosen role at registration
	
	@NotNull
    @NotEmpty
	private String registrationRole;
	
	//fr : français & en : anglais
	
	@NotNull
    @NotEmpty
	private String codeIsoLang;
	
	
	
	

}
