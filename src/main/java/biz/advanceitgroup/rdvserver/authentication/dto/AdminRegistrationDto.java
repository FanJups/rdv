package biz.advanceitgroup.rdvserver.authentication.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import biz.advanceitgroup.rdvserver.authentication.validation.PasswordMatches;
import biz.advanceitgroup.rdvserver.authentication.validation.ValidEmail;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@PasswordMatches
public class AdminRegistrationDto {
	
	private String firstName;
	private String lastName;
	private String nickName;
	
	@NotNull
    @NotEmpty
	private String password;
	private String matchingPassword;
	
	@ValidEmail
	@NotNull
    @NotEmpty
	private String email;
	
	private String registrationRole;
	private String codeIsoLang;

}
