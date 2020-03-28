package biz.advanceitgroup.rdvserver.authentication.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import biz.advanceitgroup.rdvserver.authentication.annotation.ValidEmail;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserLoginDto {
	
	@ValidEmail
	@NotNull
    @NotEmpty
	private String email;
	
	@NotNull
    @NotEmpty
	private String password;
	
	// The chosen role at login
	
	
	@NotNull
    @NotEmpty
	private String role;
	
	private String codeIsoLang;

}
