package biz.advanceitgroup.rdvserver.authentication.services.interfaces;

import biz.advanceitgroup.rdvserver.authentication.dto.UserRegistrationDto;
import biz.advanceitgroup.rdvserver.authentication.entities.User;
import biz.advanceitgroup.rdvserver.authentication.entities.VerificationToken;
import biz.advanceitgroup.rdvserver.authentication.exception.RoleDoesntExistException;
import biz.advanceitgroup.rdvserver.authentication.exception.UserAlreadyExistException;

/**
 * 
 * @author Fanon Jupkwo
 *
 */
public interface UserService {
	
	public User registerNewUserAccount(UserRegistrationDto userDto) throws UserAlreadyExistException,RoleDoesntExistException;
	
	void createVerificationTokenForUser(User user, String token);
	
	
	String validateVerificationToken(String token);
	
	User getUser(String verificationToken);

}
