package biz.advanceitgroup.rdvserver.configurations;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;

import biz.advanceitgroup.rdvserver.authentication.security.oauth2.CustomOAuth2UserService;



@Configuration
@EnableWebSecurity(debug=true)

@EnableGlobalMethodSecurity(
		securedEnabled = true,
		jsr250Enabled = true,
		prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter  {
	
	
	
	@Qualifier("userDetailsServiceImpl")
    @Autowired
    private UserDetailsService userDetailsService;
	
	@Autowired
    private CustomOAuth2UserService customOAuth2UserService;

	
	@Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	 
	 
	 @Override
	    protected void configure(HttpSecurity http) throws Exception {
	       /* 
	        http
	        .authorizeRequests(a -> a
				.antMatchers("/","/user/registerUserAccount",
			         "/user/activateEmailAccount",
			           "/user/login").permitAll()
						
			)
	        .oauth2Login()
	        .disable();
	        
	        */
		 
		 
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
			 .authorizeRequests()
			 .antMatchers( "/oauth2/**")
             .permitAll()
			 .antMatchers("/","/user/registerUserAccount",
				         "/user/activateEmailAccount",
				           "/user/login")
			 .permitAll()
			
			 .anyRequest()
			 .authenticated()
			 .and()
			 .oauth2Login()
				 .authorizationEndpoint()
				 	.baseUri("/oauth2/authorization")
				 	.and()
				 .userInfoEndpoint()
					 .userService(this.oauth2UserService());
		 
	 }
					 
	 
	 
	 @Bean
	    public AuthenticationManager customAuthenticationManager() throws Exception {
	        return authenticationManager();
	    }
	    
	 
	 @Override
		public void configure(WebSecurity web) throws Exception {
			web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**", "/configuration/**",
					"/swagger-ui.html", "/webjars/**");
		}
	 
	 
	 
	 @Autowired
	    public void configure(AuthenticationManagerBuilder auth) throws Exception {
	        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	    }
	 
	 @EventListener
	    public void authSuccessEventListener(AuthenticationSuccessEvent authorizedEvent){
	        // write custom code here for login success audit
	        System.out.println("User Oauth2 login success");
	        System.out.println("This is success event : "+authorizedEvent.getAuthentication().getPrincipal());
	    }

	    @EventListener
	    public void authFailedEventListener(AbstractAuthenticationFailureEvent oAuth2AuthenticationFailureEvent){
	        // write custom code here login failed audit.
	        System.out.println("User Oauth2 login Failed");
	        System.out.println(oAuth2AuthenticationFailureEvent.getAuthentication().getPrincipal());
	    }
	    
	    private OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2UserService() {
			return new CustomOAuth2UserService();
		}
	    
	    


}
