package biz.advanceitgroup.rdvserver.authentication.services.interfaces;

import biz.advanceitgroup.rdvserver.authentication.dto.AdminRegistrationDto;
import biz.advanceitgroup.rdvserver.authentication.dto.UpdateProfileInformationDto;
import biz.advanceitgroup.rdvserver.authentication.dto.UserRegistrationDto;
import biz.advanceitgroup.rdvserver.authentication.entities.User;

import biz.advanceitgroup.rdvserver.authentication.exception.RoleDoesntExistException;
import biz.advanceitgroup.rdvserver.authentication.exception.UserAlreadyExistException;

/**
 * 
 * @author Fanon Jupkwo
 *
 */
public interface UserService {
	
	User registerNewUserAccount(UserRegistrationDto userDto) throws UserAlreadyExistException,RoleDoesntExistException;
	
	User registerNewAdminAccount(AdminRegistrationDto adminRegistrationDto) throws UserAlreadyExistException,RoleDoesntExistException;
	
	void createVerificationTokenForUser(User user, String token);
	
	
	String validateVerificationToken(String token);
	
	User getUser(String verificationToken);
	
	User findByEmail(String email);
	
	User findById(Long id);
	
	void updateProfileInformation(Long id,UpdateProfileInformationDto updateProfileInformationDto);
	
	void setPaymentInfosById(String paymentMode, String paymentAccount, long id);

}
