package biz.advanceitgroup.rdvserver.authentication.services.impl;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import biz.advanceitgroup.rdvserver.authentication.dto.AdminRegistrationDto;
import biz.advanceitgroup.rdvserver.authentication.dto.UpdateProfileInformationDto;
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
	
	public static final String TOKEN_INVALID = "invalidToken";
    public static final String TOKEN_EXPIRED = "expired";
    public static final String TOKEN_VALID = "valid";
    
    
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
		 
		
		 user.setEncryptPwd(passwordEncoder.encode(userDto.getPassword()));
		 user.setEmail(userDto.getEmail());
		 user.setRegistrationRole(userDto.getRegistrationRole());
		 
		 Set<Role> roles = new HashSet<>(Arrays.asList(roleRepository.findByName(userDto.getRegistrationRole())));
		 
		 user.setRoles(roles);
		 
		
	     return userRepository.save(user);
		 
	 } 
	 
	 @Override
	 public User registerNewAdminAccount(AdminRegistrationDto adminRegistrationDto)
	 {
		 
		 if(emailExists(adminRegistrationDto.getEmail()))
		 {
			 throw new UserAlreadyExistException("There is an account with that email adress: " + adminRegistrationDto.getEmail());
			 
		 }
		 
		 if(roleDoesntExist(adminRegistrationDto.getRegistrationRole()))
		 {
			 throw new RoleDoesntExistException("This role doesn't exist: " + adminRegistrationDto.getRegistrationRole());
			 
			 
			 
			 
		 }
		 
		 
		 
		 User user = new User();
		 
		 user.setFirstName(adminRegistrationDto.getFirstName());
		 user.setLastName(adminRegistrationDto.getLastName());
		 user.setNickName(adminRegistrationDto.getNickName());
		 
		
		 user.setEncryptPwd(passwordEncoder.encode(adminRegistrationDto.getPassword()));
		 user.setEmail(adminRegistrationDto.getEmail());
		 
		 Set<Role> roles = new HashSet<>(Arrays.asList(roleRepository.findByName(adminRegistrationDto.getRegistrationRole())));
		 
		 
		 user.setRegistrationRole(adminRegistrationDto.getRegistrationRole());
		 
		 roles.add(roleRepository.findByName(Role.ROLE_ADMIN));
		 
		 user.setRoles(roles);
		 
		
	     return userRepository.save(user);
		 
	 } 
	 
	 
	 @Override
	    public void createVerificationTokenForUser(final User user, final String token) {
	        final VerificationToken myToken = new VerificationToken(token, user);
	        tokenRepository.save(myToken);
	    }
	 
	 @Override
	    public String validateVerificationToken(String token) {
	        final VerificationToken verificationToken = tokenRepository.findByToken(token);
	        if (verificationToken == null) {
	            return TOKEN_INVALID;
	        }

	        final User user = verificationToken.getUser();
	        final Calendar cal = Calendar.getInstance();
	        if ((verificationToken.getExpiryDate()
	            .getTime()
	            - cal.getTime()
	                .getTime()) <= 0) {
	            tokenRepository.delete(verificationToken);
	            return TOKEN_EXPIRED;
	        }

	        
	        //Le compte a un statut activé
	        user.setEnabled(true);
	        
	        
	        
	        User.updateAccountStatus(user);
	        
	        //Token désactivé
	        tokenRepository.delete(verificationToken);
	        
            Set<Role> roles = user.getRoles();
            
            Role workerRole = roleRepository.findByName(Role.ROLE_EMPLOYER);
            
            
            //Si le registerRole c'est fournisseur
            if(roles.contains(workerRole))
            	user.setValidated(true);
	        
	        
	        User.updateAccountStatus(user);
            
            userRepository.save(user);
	        return TOKEN_VALID;
	    }
	 
	 @Override
	    public User getUser(final String verificationToken) {
	        final VerificationToken token = tokenRepository.findByToken(verificationToken);
	        if (token != null) {
	            return token.getUser();
	        }
	        return null;
	    }
	 
	 
	 
	
	 public User findByEmail (String email)
	 {
		 return userRepository.findByEmail(email).get();
	 }
	 
	 public User findById (Long id)
	 {
		 return userRepository.findById(id).get();
	 }
	 
	 public void updateProfileInformation(Long id,UpdateProfileInformationDto updateProfileInformationDto)
	 {
		 
		 
		 
		  userRepository.updateProfileInformationById(
				  updateProfileInformationDto.getFirstName(),
				  updateProfileInformationDto.getLastName(),
				  updateProfileInformationDto.getNickName(),
				  updateProfileInformationDto.getPhoneNumber(),
				  updateProfileInformationDto.getPersonnalAddress(),
				  updateProfileInformationDto.getProfessionnalAddress(),
				  updateProfileInformationDto.getActivityRadius(),
				  updateProfileInformationDto.getBusinessDescription(),
				  updateProfileInformationDto.getPaymentMode(),
				  updateProfileInformationDto.getPaymentAccount(),
				  id);
		 
		 
	 }
	 
	 public void setPaymentInfosById(String paymentMode, String paymentAccount, long id)
	 {
		 userRepository.setPaymentInfosById(paymentMode, paymentAccount, id);
	 }
	 
	 private boolean emailExists(final String email) {
	        return userRepository.existsByEmail(email);
	    }
	 
	 private boolean roleDoesntExist(final String name) {
	        
	        return roleRepository.findByName(name) ==null;
	    }

	    

}
