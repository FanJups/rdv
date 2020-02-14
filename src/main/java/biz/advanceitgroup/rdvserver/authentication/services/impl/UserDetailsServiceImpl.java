package biz.advanceitgroup.rdvserver.authentication.services.impl;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import biz.advanceitgroup.rdvserver.authentication.entities.User;
import biz.advanceitgroup.rdvserver.authentication.exception.ResourceNotFoundException;
import biz.advanceitgroup.rdvserver.authentication.repository.UserRepository;
import biz.advanceitgroup.rdvserver.authentication.security.UserPrincipal;


/*
  https://docs.spring.io/spring-security/site/docs/3.2.x/apidocs/org/springframework/security/core/userdetails/User.html
 */

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
    private UserRepository userRepository;
	
	@Override
    @Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
       
		User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with email : " + email)
        );
                

        return UserPrincipal.create(user);
    }
	
	@Transactional
    public UserDetails loadUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("User", "id", id)
        );

        return UserPrincipal.create(user);
    }

}
