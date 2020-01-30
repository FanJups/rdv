package biz.advanceitgroup.rdvserver.authentication.entities;


import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;


import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;





/**
 * Cette classe représente un utilisateur (WORKER, EMPLOYER, ADMIN) sur la plateforme rendezvousservices.
 * @author Fanon Jupkwo
 *
 */


@Entity
@Data
@AllArgsConstructor


public class User {
	
	
	@Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String email;
	private String password;
	
	@Column(name = "enabled")
	private boolean enabled;
	
	//private boolean isUsing2FA;
	
	//private String secret;
	
	
    
 
    @ManyToMany
    @JsonManagedReference // Avoiding this error Expected ',' instead of ''
    @JoinTable( 
        name = "users_roles", 
        joinColumns = @JoinColumn(
          name = "user_id", referencedColumnName = "id"), 
        inverseJoinColumns = @JoinColumn(
          name = "role_id", referencedColumnName = "id")) 
    private Set<Role> roles;
    
    public User() {
    	
    	super();
        this.enabled=false;
        
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((email == null) ? 0 : email.hashCode());
        return result;
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User user = (User) obj;
        if (!email.equals(user.email)) {
            return false;
        }
        return true;
    }

}
