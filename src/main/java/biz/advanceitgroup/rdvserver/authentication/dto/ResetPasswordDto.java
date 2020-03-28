package biz.advanceitgroup.rdvserver.authentication.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import biz.advanceitgroup.rdvserver.authentication.annotation.PasswordMatchesResetPasswordDto;
import biz.advanceitgroup.rdvserver.authentication.annotation.ValidEmail;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@PasswordMatchesResetPasswordDto
public class ResetPasswordDto {
	
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

	
	@NotNull
    @NotEmpty
	private String codeIsoLang;



}
