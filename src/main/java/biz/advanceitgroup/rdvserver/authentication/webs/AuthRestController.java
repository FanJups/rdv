package biz.advanceitgroup.rdvserver.authentication.webs;

import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import biz.advanceitgroup.rdvserver.authentication.OnRegistrationCompleteEvent;
import biz.advanceitgroup.rdvserver.authentication.dto.UserRegistrationDto;
import biz.advanceitgroup.rdvserver.authentication.entities.User;
import biz.advanceitgroup.rdvserver.authentication.services.interfaces.SecurityService;
import biz.advanceitgroup.rdvserver.authentication.services.interfaces.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@RestController
@RequestMapping(produces = "application/json", value = "/api/auth")
@CrossOrigin(origins = "*")
public class AuthRestController {
	
	@Autowired
	private UserService userService;
	
	
	
	@Autowired
    private ApplicationEventPublisher eventPublisher;
	
	
	@Value("${spring.profiles.active:}")
	private String activeProfile;
	
	@PostMapping("/register")
	public ResponseEntity<?> registerUserAccount (@Valid @RequestBody UserRegistrationDto userRegistrationDto,HttpServletRequest request)
	{
		
		User user = userService.registerNewUserAccount(userRegistrationDto);
		
		
		
		eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user, request.getLocale(), getAppUrl(request)));
		
		return new ResponseEntity<>(user,HttpStatus.OK) ;
		
	}
	
	
	
	private String getAppUrl(HttpServletRequest request) {
		
		
			return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	
	}
	

}
