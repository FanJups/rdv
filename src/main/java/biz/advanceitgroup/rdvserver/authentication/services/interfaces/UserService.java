package biz.advanceitgroup.rdvserver.authentication.services.interfaces;

import biz.advanceitgroup.rdvserver.admins.dto.AdminRegistrationDto;
import biz.advanceitgroup.rdvserver.authentication.dto.UpdateProfileInformationDto;
import biz.advanceitgroup.rdvserver.authentication.dto.UserRegistrationDto;
import biz.advanceitgroup.rdvserver.authentication.entities.User;
import biz.advanceitgroup.rdvserver.authentication.exceptions.RoleDoesntExistException;
import biz.advanceitgroup.rdvserver.authentication.exceptions.UserAlreadyExistException;

import java.util.List;

import org.springframework.security.oauth2.core.oidc.user.OidcUser;

/**
 * 
 * @author Fanon Jupkwo
 *
 */
public interface UserService {
	
	User registerNewUserAccount(UserRegistrationDto userDto) throws UserAlreadyExistException,RoleDoesntExistException;
	
	User registerUserAccountByGG(OidcUser oidcUser,String role) throws UserAlreadyExistException;
	
	User registerNewAdminAccount(AdminRegistrationDto adminRegistrationDto) throws UserAlreadyExistException,RoleDoesntExistException;
	
	void updatePassword(String newPassword , String email);
	
	void createVerificationTokenForUser(User user, String token);
	
	void createForgottenPasswordTokenForUser(User user, String token);
	
	
	String validateVerificationToken(String token);
	
	String validateForgottenPasswordToken(String token);
	
	User getUserFromVerificationToken(String verificationToken);
	
	User getUserFromForgottenPasswordToken(String forgottenPasswordToken);
	
	User findByEmail(String email);
	
	User findById(Long id);
	
	void updateProfileInformation(Long id,UpdateProfileInformationDto updateProfileInformationDto);
	
	void setPaymentInfosById(String paymentMode, String paymentAccount, long id);

	User findByNickName(String username);

	List<User> findAllAdmin();
	
	boolean emailExists(String email);
	
	boolean roleExists(String name);

}
