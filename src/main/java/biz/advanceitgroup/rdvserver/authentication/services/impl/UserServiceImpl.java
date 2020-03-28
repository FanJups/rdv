package biz.advanceitgroup.rdvserver.authentication.services.impl;

import java.util.*;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import biz.advanceitgroup.rdvserver.admins.dto.AdminRegistrationDto;
import biz.advanceitgroup.rdvserver.authentication.dto.UpdateProfileInformationDto;
import biz.advanceitgroup.rdvserver.authentication.dto.UserRegistrationDto;
import biz.advanceitgroup.rdvserver.authentication.entities.ForgottenPasswordToken;
import biz.advanceitgroup.rdvserver.authentication.entities.Role;
import biz.advanceitgroup.rdvserver.authentication.entities.User;
import biz.advanceitgroup.rdvserver.authentication.entities.VerificationToken;
import biz.advanceitgroup.rdvserver.authentication.entities.enumeration.AuthProvider;
import biz.advanceitgroup.rdvserver.authentication.exceptions.RoleDoesntExistException;
import biz.advanceitgroup.rdvserver.authentication.exceptions.UserAlreadyExistException;
import biz.advanceitgroup.rdvserver.authentication.repositories.ForgottenPasswordTokenRepository;
import biz.advanceitgroup.rdvserver.authentication.repositories.RoleRepository;
import biz.advanceitgroup.rdvserver.authentication.repositories.UserRepository;
import biz.advanceitgroup.rdvserver.authentication.repositories.VerificationTokenRepository;
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
    private VerificationTokenRepository verificationTokenRepository;
	
	@Autowired
    private ForgottenPasswordTokenRepository forgottenPasswordTokenRepository;
	
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
		 
		 if(! roleExists(userDto.getRegistrationRole()))
		 {
			 throw new RoleDoesntExistException("This role doesn't exist: " + userDto.getRegistrationRole());
			 
			 
			 
			 
		 }
		 
		 
		 
		 User user = new User();
		 
		
		 user.setEncryptPwd(passwordEncoder.encode(userDto.getPassword()));
		 user.setEmail(userDto.getEmail());
		 user.setRegistrationRole(userDto.getRegistrationRole());
		 user.setProvider(AuthProvider.local);
		 
		 Set<Role> roles = new HashSet<>(Arrays.asList(roleRepository.findByName(userDto.getRegistrationRole())));
		 
		 user.setRoles(roles);
		 
		
	     return userRepository.save(user);
		 
	 } 
	 
	 public void updatePassword(String newPassword , String email)
	 {
		 userRepository.updatePassword(passwordEncoder.encode(newPassword), email);
	 }
	 
	 public User registerUserAccountByGG(OidcUser oidcUser,String role)
	 {
		 
		 if(emailExists(oidcUser.getEmail()))
		 {
			 throw new UserAlreadyExistException("There is an account with that email adress: " + oidcUser.getEmail());
			 
		 }
		 
		 User user = new User();

	     user.setProvider(AuthProvider.google);
	       
	     user.setName(oidcUser.getName());
	     user.setEmail(oidcUser.getEmail());
	     user.setEnabled(true);
	     user.setGender(oidcUser.getGender());
	     user.setRegistrationRole(role);
	        
	     return userRepository.save(user);
		 
		 
	 }
	 
	 @Override
	 public User registerNewAdminAccount(AdminRegistrationDto adminRegistrationDto)
	 {
		 
		 if(emailExists(adminRegistrationDto.getEmail()))
		 {
			 throw new UserAlreadyExistException("There is an account with that email adress: " + adminRegistrationDto.getEmail());
			 
		 }
		 
		 if(! roleExists(adminRegistrationDto.getRegistrationRole()))
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
	        verificationTokenRepository.save(myToken);
	    }
	 
	 @Override
	    public void createForgottenPasswordTokenForUser(final User user, final String token) {
	        final ForgottenPasswordToken myToken = new ForgottenPasswordToken(token, user);
	        forgottenPasswordTokenRepository.save(myToken);
	    }

	 
	 @Override
	    public String validateVerificationToken(String token) {
	        final VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
	        if (verificationToken == null) {
	            return TOKEN_INVALID;
	        }

	        final User user = verificationToken.getUser();
	        final Calendar cal = Calendar.getInstance();
	        if ((verificationToken.getExpiryDate()
	            .getTime()
	            - cal.getTime()
	                .getTime()) <= 0) {
	        	verificationTokenRepository.delete(verificationToken);
	            return TOKEN_EXPIRED;
	        }

	        
	        //Le compte a un statut activé
	        user.setEnabled(true);
	        
	        
	        
	        User.updateAccountStatus(user);
	        
	        //Token désactivé
	        verificationTokenRepository.delete(verificationToken);
	        
            Set<Role> roles = user.getRoles();
            
            Role workerRole = roleRepository.findByName(Role.ROLE_EMPLOYER);
            
            
            //Si le registerRole c'est fournisseur
            if(roles.contains(workerRole))
            	user.setValidated(true);
	        
	        
	        //Validated=true
            User.updateAccountStatus(user);
            
            userRepository.save(user);
	        return TOKEN_VALID;
	    }
	 
	 @Override
	    public String validateForgottenPasswordToken(String token) {
	        final ForgottenPasswordToken forgottenPasswordToken = forgottenPasswordTokenRepository.findByToken(token);
	        if (forgottenPasswordToken == null) {
	            return TOKEN_INVALID;
	        }

	        final Calendar cal = Calendar.getInstance();
	        if ((forgottenPasswordToken.getExpiryDate()
	            .getTime()
	            - cal.getTime()
	                .getTime()) <= 0) {
	        	forgottenPasswordTokenRepository.delete(forgottenPasswordToken);
	            return TOKEN_EXPIRED;
	        }

	       
	        //Token désactivé
	        forgottenPasswordTokenRepository.delete(forgottenPasswordToken);
	       return TOKEN_VALID;
	    }
	 
	 @Override
	    public User getUserFromVerificationToken(final String verificationToken) {
	        final VerificationToken token = verificationTokenRepository.findByToken(verificationToken);
	        if (token != null) {
	            return token.getUser();
	        }
	        return null;
	    }
	 
	
	 @Override
	    public User getUserFromForgottenPasswordToken(final String forgottenPasswordToken) {
	        final ForgottenPasswordToken token = forgottenPasswordTokenRepository.findByToken(forgottenPasswordToken);
	        if (token != null) {
	            return token.getUser();
	        }
	        return null;
	    }

	 
	
	 public User findByEmail (String email)
	 {
		 return userRepository.findByEmail(email).get();
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
	 
	 public boolean emailExists(final String email) {
	        return userRepository.existsByEmail(email);
	    }
	 
	 /*private boolean roleDoesntExist(final String name) {
	 	return roleRepository.findByName(name) ==null;
	 	
	 	roleRepository.existsByName(name);
	 }*/
	 
	 public boolean roleExists(final String name) {
		 	
		 	return roleRepository.existsByName(name);
		 }


	public User findByNickName( String username ) {
		return  userRepository.findByNickName(username);
	}

	public User findById( Long id ) {
		Optional<User> user = userRepository.findById( id );
		return user.orElse(null);
	}

	@Override
	public List<User> findAllAdmin() {
		return userRepository.findAllAdmin();
	}

	    

}
