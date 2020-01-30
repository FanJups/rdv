package biz.advanceitgroup.rdvserver.configurations;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;



@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter  {
	
	/*
	@Autowired
    private CustomAuthenticationProvider authProvider;
 
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider);
    }
    */
	
	//@Qualifier("userDetailsServiceImpl")
    //@Autowired
    //private UserDetailsService userDetailsService;
	
	@Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	 
	 
	 @Override
	    protected void configure(final HttpSecurity http) throws Exception {
	        
	        http.cors()
			    .and()
	            .csrf()
	            .disable()
	            .authorizeRequests()
	            .antMatchers("/","/api/auth/register").anonymous();
	            
	        
	        /* Spring Security will never create an HttpSession and it will never use it to obtain the SecurityContext*/
	        //http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

	     /* access rights accorder */
	        //http.authorizeRequests().antMatchers("/login/**","/**").permitAll();
	       // http.authorizeRequests().antMatchers("/").hasAnyAuthority("USER");
	        //http.authorizeRequests().anyRequest().authenticated();
	                
	    
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
	 
	 /*
	 
	 @Autowired
	    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	    }
	    
	    */


}
