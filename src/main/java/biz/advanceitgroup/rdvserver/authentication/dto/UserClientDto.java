package biz.advanceitgroup.rdvserver.authentication.dto;

import java.util.Set;

import biz.advanceitgroup.rdvserver.authentication.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserClientDto {
	
	private String email;
	private String encryptPwd;
	private boolean enabled;
	private boolean validated;
	private int accountStatus;
	private Set<Role> roles;
	
	private String firstName;
	private String lastName;
	private String nickName;
	
	private String registrationRole;
	private String codeIsoLang;

}
