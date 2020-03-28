package biz.advanceitgroup.rdvserver.admins.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import biz.advanceitgroup.rdvserver.authentication.annotation.PasswordMatchesUserRegistrationDto;
import biz.advanceitgroup.rdvserver.authentication.annotation.ValidEmail;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@PasswordMatchesUserRegistrationDto
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
