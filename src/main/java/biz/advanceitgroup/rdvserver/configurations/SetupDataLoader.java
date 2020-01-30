package biz.advanceitgroup.rdvserver.configurations;


import java.util.Arrays;
import java.util.HashSet;

import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Component;

import biz.advanceitgroup.rdvserver.authentication.entities.Role;
import biz.advanceitgroup.rdvserver.authentication.entities.User;
import biz.advanceitgroup.rdvserver.authentication.repository.RoleRepository;
import biz.advanceitgroup.rdvserver.authentication.repository.UserRepository;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {
	
	private boolean alreadySetup = false;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
 

    @Override
    @Transactional
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }

        
        // == create initial roles
        final Role adminRole = createRoleIfNotFound("ROLE_ADMIN");
        createRoleIfNotFound("ROLE_WORKER");
        createRoleIfNotFound("ROLE_EMPLOYER");
        
        Set<Role> roles = new HashSet<>(Arrays.asList(adminRole));

        // == create initial user
        createUserIfNotFound("test@test.com", "Test", roles);

        alreadySetup = true;
    }

    

    @Transactional
    private final Role createRoleIfNotFound(final String name) {
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
        }
        
        role = roleRepository.save(role);
        return role;
    }

    @Transactional
    private final User createUserIfNotFound(final String email,  String password,  Set<Role> roles) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            user = new User();
            
            user.setPassword(passwordEncoder.encode(password));
            user.setEmail(email);
            
            
        }
        user.setRoles(roles);
        user = userRepository.save(user);
        return user;
    }

}
