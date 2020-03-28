package biz.advanceitgroup.rdvserver.authentication.security.oauth2;

import static biz.advanceitgroup.rdvserver.authentication.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository.ROLE_PARAM_COOKIE_NAME;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import biz.advanceitgroup.rdvserver.authentication.entities.User;
import biz.advanceitgroup.rdvserver.authentication.exceptions.RoleDoesntExistException;
import biz.advanceitgroup.rdvserver.authentication.repositories.RoleRepository;
import biz.advanceitgroup.rdvserver.authentication.repositories.UserRepository;
import biz.advanceitgroup.rdvserver.authentication.security.TokenProvider;
import biz.advanceitgroup.rdvserver.authentication.services.interfaces.UserService;
import biz.advanceitgroup.rdvserver.authentication.util.CookieUtils;
import biz.advanceitgroup.rdvserver.commons.entities.AuthResponse;


@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	
	 @Autowired
	private TokenProvider tokenProvider;
	 
	   @Autowired
	    private UserService userService;
	    
	    @Autowired
	    private UserRepository userRepository;
	    
	    
	    @Autowired
	    private RoleRepository roleRepository;
	    

		private  AuthResponse authResponse;
	
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        /*
    	String targetUrl = determineTargetUrl(request, response, authentication);

        if (response.isCommitted()) {
            logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return
        }

        clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
        */
    	
    	
			String role = getRole(request,response,authentication);
			
			
	    	// write custom code here for login success audit
	        System.out.println("User Oauth2 login success");
	        System.out.println("This is success event : "+authentication.getPrincipal());
	        
	       
	        //Google only works
	       
	        
	        OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();
	        
	        //oAuth2User.
	        
	      
	        System.out.println("OAUTH : "+oAuth2User.getAttributes()); 
	        
	        //OidcUser
	        
	        
	        OidcUser oidcUser = (OidcUser) authentication.getPrincipal();
	        
	        System.out.println(" GOOGLE USER : "+oidcUser);
	        
	        // If Google is the Open Id Connect Provider
	        // Google issuer : https://accounts.google.com
	        if(oidcUser.getIssuer().toString().contains("google"))
	        {
	        	Optional<User> userOptional = userRepository.findByEmail(oidcUser.getEmail());
		        
		        User user;
		        
		        SecurityContextHolder.getContext().setAuthentication(authentication);
		        
		        if(userOptional.isPresent())
		        {
		        	//loginGG
		        	user = userOptional.get();
		        	
		        
		        }else {
		        	
		        	//registerUserAccountByGG
		        	
		        	user = userService.registerUserAccountByGG(oidcUser,role); 
		        	
		        	
		        }
		        
		        setAuthResponse(generatingAuthResponse(user,role));
		        
		       
	        	
	        }
	        

    }
    
    
    
    
    private String getRole(HttpServletRequest request, HttpServletResponse response, Authentication authentication) 
    {
    	
    	
    	Optional<String> role = CookieUtils.getCookie(request, ROLE_PARAM_COOKIE_NAME)
                .map(Cookie::getValue);
    	
    	
    	
    	if(role.isPresent())
    	{
    		if(roleRepository.existsByName(role.get()))
    		{
    			
    		}else {
    			
    			throw new RoleDoesntExistException("This role doesn't exist: " + role.get());
        		
    			
    			
    		}
    		
    	}else {
    		
    		throw new RoleDoesntExistException("Sorry! Role parameter should not be empty !");
    		
    		
    		
    	}
    	
    	
       
    	
    	return role.get();
    	
    }
    
    private AuthResponse generatingAuthResponse(User user,String role)
	 {
		 
		 String token = tokenProvider.createToken(user.getId());
  	 
		 return new AuthResponse(token,user.getId(),role,tokenProvider.getExpirationDate(token));
	 }




	/**
	 * @return the authResponse
	 */
	public AuthResponse getAuthResponse() {
		return authResponse;
	}




	/**
	 * @param authResponse the authResponse to set
	 */
	private void setAuthResponse(AuthResponse authResponse) {
		this.authResponse = authResponse;
	}
    
    

}
