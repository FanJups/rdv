package biz.advanceitgroup.rdvserver.configurations;


import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.BeanIds;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import biz.advanceitgroup.rdvserver.authentication.security.oauth2.CustomOAuth2UserService;
import biz.advanceitgroup.rdvserver.authentication.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository;
import biz.advanceitgroup.rdvserver.authentication.security.oauth2.OAuth2AuthenticationSuccessHandler;
import biz.advanceitgroup.rdvserver.authentication.security.oauth2.user.OAuth2UserInfo;
import biz.advanceitgroup.rdvserver.authentication.services.interfaces.UserService;
import biz.advanceitgroup.rdvserver.authentication.webs.AuthRestController;
import biz.advanceitgroup.rdvserver.commons.entities.AuthResponse;
import biz.advanceitgroup.rdvserver.authentication.entities.User;
import biz.advanceitgroup.rdvserver.authentication.entities.enumeration.AuthProvider;
import biz.advanceitgroup.rdvserver.authentication.repositories.UserRepository;
import biz.advanceitgroup.rdvserver.authentication.security.TokenAuthenticationFilter;
import biz.advanceitgroup.rdvserver.authentication.security.TokenProvider;
import biz.advanceitgroup.rdvserver.authentication.security.UserPrincipal;



@Configuration
@EnableWebSecurity(debug=true)

@EnableGlobalMethodSecurity(
		securedEnabled = true,
		jsr250Enabled = true,
		prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter  {

    @Autowired
    private UserDetailsService userDetailsService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private UserRepository userRepository;
	
	@Autowired
    private CustomOAuth2UserService customOAuth2UserService;
	
	@Autowired
    private TokenProvider tokenProvider;
	
	@Autowired
    private OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
	
	
	/*@Autowired
	private AuthRestController authRestController;*/

	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	

	 @Bean(BeanIds.AUTHENTICATION_MANAGER)
		@Override
		public AuthenticationManager authenticationManagerBean() throws Exception {
			return super.authenticationManagerBean();
		}
	 
	 @Bean
	    public TokenAuthenticationFilter tokenAuthenticationFilter() {
	        return new TokenAuthenticationFilter();
	    }
	 
	    /*
	    By default, Spring OAuth2 uses HttpSessionOAuth2AuthorizationRequestRepository to save
	    the authorization request. But, since our service is stateless, we can't save it in
	    the session. We'll save the request in a Base64 encoded cookie instead.
	  */
	  @Bean
	  public HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository() {
	      return new HttpCookieOAuth2AuthorizationRequestRepository();
	  }
					 
	 
	 
	 @Override
		@Bean
		public AuthenticationManager authenticationManager() throws Exception {
			return super.authenticationManager();
		}
	    
	 
	 @Override
		public void configure(WebSecurity web) throws Exception {
			web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**", "/configuration/**",
					"/swagger-ui.html", "/webjars/**");
		}
	 
	 
	 
	 @Autowired
	    public void configure(AuthenticationManagerBuilder auth) throws Exception {
	        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	    }
	 


	    @EventListener
	    public void authFailedEventListener(AbstractAuthenticationFailureEvent oAuth2AuthenticationFailureEvent){
	        // write custom code here login failed audit.
	        System.out.println("User Oauth2 login Failed");
	        System.out.println(oAuth2AuthenticationFailureEvent.getAuthentication().getPrincipal());
	    }
	    
	    
		 
		 
		 @Override
		    protected void configure(HttpSecurity http) throws Exception {
		     
				 http
				 .cors()
		         .and()
		     .sessionManagement()
		         .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		         .and()
					 .csrf()
					 .disable()
					 .formLogin()
		             .disable()
					 .httpBasic()
					 .disable()
					.authorizeRequests(
							a -> a
						       .antMatchers("/",
										"/error",
										"/favicon.ico",
										"/**/*.png",
										"/**/*.gif",
										"/**/*.svg",
										"/**/*.jpg",
										"/**/*.html",
										"/**/*.css",
										"/**/*.js")
						       .permitAll()
						       .antMatchers("/user/registerUserAccount",
						         "/user/activateEmailAccount",
						           "/user/login")
					           .permitAll()
					           .antMatchers("/oauth2/**")
						       .permitAll()
						       .antMatchers("/user/authResponseFromSocialLogin")
						       .permitAll()
						       .antMatchers("/user/forgottenPassword","/user/resetPassword","/user/confirmResetPassword")
						       .permitAll()
						       .antMatchers("/files/**")
						       .permitAll()
						       .antMatchers("/category/**","/addCategory/**")
						       .permitAll()
						       .antMatchers("/api/job/**")
						       .permitAll()
						       .antMatchers("/api/bid/**")
						       .permitAll()
						       .anyRequest()
						       .authenticated()
					)
					.exceptionHandling(e -> e
						.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
					)/*
					.csrf(c -> c
						.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
					)*/
					
					.oauth2Login()
					.authorizationEndpoint()
				 	.baseUri("/oauth2/authorization")
				 	.authorizationRequestRepository(cookieAuthorizationRequestRepository())
				 	/*.and()
				 .userInfoEndpoint()
					 .userService(customOAuth2UserService);*/
				 	.and()
				 	.successHandler(oAuth2AuthenticationSuccessHandler);
				 
				 
				// Add our custom Token based authentication filter
			    http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
			    
			 
			 
		 }
		 
		 
		 
		
		 
		 

}
