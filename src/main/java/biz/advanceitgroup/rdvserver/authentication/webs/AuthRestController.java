package biz.advanceitgroup.rdvserver.authentication.webs;

import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import biz.advanceitgroup.rdvserver.authentication.OnRegistrationCompleteEvent;
import biz.advanceitgroup.rdvserver.authentication.dto.UserClientDto;
import biz.advanceitgroup.rdvserver.authentication.dto.UserLoginDto;
import biz.advanceitgroup.rdvserver.authentication.dto.UserRegistrationDto;

import biz.advanceitgroup.rdvserver.authentication.entities.User;

import biz.advanceitgroup.rdvserver.authentication.services.interfaces.UserService;
import io.swagger.annotations.Api;

import javax.servlet.http.HttpServletRequest;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


@RestController
@RequestMapping(produces = "application/json", value = "/user")
@CrossOrigin(origins = "*")
@Api(value = "Authentication Rest API", description = "Définition des API d'authentification.")
public class AuthRestController {
	
	@Autowired
	private UserService userService;
	
	
	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
    private ApplicationEventPublisher eventPublisher;
	
	
	
	//@Value("${spring.profiles.active:}")
	private String activeProfile;
	
	@PostMapping("/registerUserAccount")
	public ResponseEntity<?> registerUserAccount (@Valid @RequestBody UserRegistrationDto userRegistrationDto,HttpServletRequest request)
	{
		
		User user = userService.registerNewUserAccount(userRegistrationDto);
		
		eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user, request.getLocale(), getAppUrl(request)));
		
		UserClientDto userClientDto = new UserClientDto();
		
		userClientDto.setEmail(user.getEmail());
		
		userClientDto.setPassword(user.getEncryptPwd());
		
		userClientDto.setEnabled(user.isEnabled());
		
		userClientDto.setValidated(user.isValidated());
		
		userClientDto.setAccountStatus(user.getAccountStatus());
		
		userClientDto.setRoles(user.getRoles()); 
		
		
		
		return new ResponseEntity<>(userClientDto,HttpStatus.OK);
		
	}
	
	@PostMapping("/activateEmailAccount")
	public ResponseEntity<?> activateEmailAccount (@RequestParam("token") String token)
	{
		
		String result = userService.validateVerificationToken(token);
		
		
		
        if (result.equals("valid")) {
        	
            User user = userService.getUser(token);
      
    		
            
            return new ResponseEntity<>(user,HttpStatus.OK) ;
            
            	
        }

 	
        return null ;
		
	}
	

	@PostMapping("/login")
	public ResponseEntity<?> login (@Valid @RequestBody UserLoginDto userLoginDto,HttpServletRequest request)
	{
		
		try {
			
			UserDetails userDetails = userDetailsService.loadUserByUsername(userLoginDto.getEmail());
			
		    Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword());
		    
		    SecurityContext sc = SecurityContextHolder.getContext();
		    
		    sc.setAuthentication(authentication);
		    
		    return new ResponseEntity<>(userLoginDto,HttpStatus.OK) ;
		     
			
		}catch(UsernameNotFoundException e) {
			
			return null ;
			
		}
		
		
		
	}
	

	
	
	
	private String getAppUrl(HttpServletRequest request) {
		
		
			return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	
	}
	

}
