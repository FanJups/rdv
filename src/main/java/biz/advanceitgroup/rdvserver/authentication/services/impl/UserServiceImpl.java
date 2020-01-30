package biz.advanceitgroup.rdvserver.authentication.services.impl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import biz.advanceitgroup.rdvserver.authentication.dto.UserRegistrationDto;
import biz.advanceitgroup.rdvserver.authentication.entities.Role;
import biz.advanceitgroup.rdvserver.authentication.entities.User;
import biz.advanceitgroup.rdvserver.authentication.entities.VerificationToken;
import biz.advanceitgroup.rdvserver.authentication.exception.RoleDoesntExistException;
import biz.advanceitgroup.rdvserver.authentication.exception.UserAlreadyExistException;
import biz.advanceitgroup.rdvserver.authentication.repository.RoleRepository;
import biz.advanceitgroup.rdvserver.authentication.repository.UserRepository;
import biz.advanceitgroup.rdvserver.authentication.repository.VerificationTokenRepository;
import biz.advanceitgroup.rdvserver.authentication.services.interfaces.UserService;

/**
 * 
 * @author Fanon Jupkwo
 *
 */

@Service
@Transactional
public class UserServiceImpl implements UserService {
	
	@Autowired
    private UserRepository userRepository;
	
	@Autowired
    private RoleRepository roleRepository;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	@Autowired
    private VerificationTokenRepository tokenRepository;
	
	 @Override
	 public User registerNewUserAccount(UserRegistrationDto userDto)
	 {
		 
		 
		 
		 
		 if(emailExists(userDto.getEmail()))
		 {
			 throw new UserAlreadyExistException("There is an account with that email adress: " + userDto.getEmail());
			 
		 }
		 
		 if(roleDoesntExist(userDto.getRegistrationRole()))
		 {
			 throw new RoleDoesntExistException("This role doesn't exist: " + userDto.getRegistrationRole());
			 
			 
			 
			 
		 }
		 
		 
		 
		 User user = new User();
		 
		 
		 user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		 user.setEmail(userDto.getEmail());
		 
		 Set<Role> roles = new HashSet<>(Arrays.asList(roleRepository.findByName(userDto.getRegistrationRole())));
		 
		 user.setRoles(roles);
		 
		
	     return userRepository.save(user);
		 
	 } 
	 
	 @Override
	    public void createVerificationTokenForUser(final User user, final String token) {
	        final VerificationToken myToken = new VerificationToken(token, user);
	        tokenRepository.save(myToken);
	    }
	 
	 private boolean emailExists(final String email) {
	        return userRepository.findByEmail(email) != null;
	    }
	 
	 private boolean roleDoesntExist(final String name) {
	        
	        return roleRepository.findByName(name) ==null;
	    }
	    

}
