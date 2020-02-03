package biz.advanceitgroup.rdvserver.authentication.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import biz.advanceitgroup.rdvserver.authentication.entities.Role;
import biz.advanceitgroup.rdvserver.authentication.entities.User;

import java.util.Collection;

import java.util.HashSet;

import java.util.Map;
import java.util.Set;

public class UserPrincipal implements OAuth2User, UserDetails {
	
	private Long id;
    private String email;
    private String password;
    private boolean enabled;
    private Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes;
    
    public UserPrincipal(Long id, String email, String password,boolean enabled, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.enabled =enabled;
        this.authorities = authorities;
    }
    
    public static UserPrincipal create(User user) {
    	Set<GrantedAuthority> authorities = new HashSet<>();
        for (Role role : user.getRoles()){
        	authorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        return new UserPrincipal(
                user.getId(),
                user.getEmail(),
                user.getEncryptPwd(),
                user.isEnabled(),
                authorities
        );
    }
    
    public static UserPrincipal create(User user, Map<String, Object> attributes) {
        UserPrincipal userPrincipal = UserPrincipal.create(user);
        userPrincipal.setAttributes(attributes);
        return userPrincipal;
    }
    
    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }
    
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getName() {
        return String.valueOf(id);
    }

}
