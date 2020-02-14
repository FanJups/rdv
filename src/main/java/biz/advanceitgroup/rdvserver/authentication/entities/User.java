package biz.advanceitgroup.rdvserver.authentication.entities;


import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
	
	@Column(unique = true, nullable = false, length= 50)
	private String email;
	
	private String imageUrl;
	
	//@NotNull
    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    private String providerId;
    
    // name from provider : google or facebook
    private String name;
	
	
	@Column(nullable = false, length= 500)
	private String encryptPwd;
	
	@Column(name = "enabled")
	private boolean enabled;
	
	
	private boolean validated;
	
	/*
	 
	 accountStatus
	 
	 0 = DISABLED
	 1 = ACTIVATED
	 2 = VALIDATED
	 
	 */
	
	private int accountStatus;
	
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
    
    
    @ManyToMany
    @JsonManagedReference // Avoiding this error Expected ',' instead of ''
    @JoinTable( 
        name = "users_categories", 
        joinColumns = @JoinColumn(
          name = "user_id", referencedColumnName = "id"), 
        inverseJoinColumns = @JoinColumn(
          name = "category_id", referencedColumnName = "categoryID")) 
    private Set<Category> categories;
    
    
    @JsonBackReference
    @OneToMany(mappedBy = "userID",fetch = FetchType.LAZY)
    private Set<File> files;
    
    
    @JsonBackReference
    @OneToMany(mappedBy = "userID",fetch = FetchType.LAZY)
    private Set<Bid> bids;
    
    
    
    /*
     
     0 = WORKER
     1 = PROVIDER
     2 = WORKER & PROVIDER
     3 = ADMIN
     
     */
    private int registerRole;
    
    private String registrationRole;
    
    @Column(length= 50)
    private String firstName;
    
    @Column(length= 50)
    private String lastName;
    
    @Column(length= 50)
    private String nickName;
    
    @Column(length= 25)
    private String phoneNumber;
    
    @Column(length= 500)
    private String personnalAddress;
    
    //M= Male; F=Female
    
    @Column(length= 1)
    private char gender;
    
    
    @Column(length= 500)
    private String professionnalAddress;
    
    private long activityRadius;
    
    @Column(length= 500)
    private String businessDescription;
    
    private String paymentMode;
	private String paymentAccount;
    
    @Column(length= 50)
    private String paypalAccount;
    
    @Column(length= 50)
    private String stripeAccount;
    
    
    
    private long personnalAddressProofFileID;
    private long professionnalAddressProofFileID;
    
    @Column(length= 500)
    private long personnalAddressProofFileURL;
    
    @Column(length= 500)
    private String professionnalAddressProofFileURL;
    
    private long profileImageID;
    
    @Column(length= 500)
    private String profileImageURL;
    
    private Timestamp registerDate;
    
    private Timestamp activationDate;
    
    private Timestamp validationDate;
    
    private Timestamp suspensionDate;
    
    private Timestamp suspensionLiftingDate;
    
    private Timestamp lastConnectionDate;
    
    private Timestamp createDate;
    
    private Timestamp updateDate;
    
    
    @Column(length= 100)
    private String createUserName="SYSTEM";
    
    
    @Column(length= 100)
    private String updateUserName="SYSTEM";
    
    private double professionnalAddressLongitude;
    
    private double professionnalAddressLatitude;
    
    
    
    public User() {
    	
    	super();
        this.enabled=false;
        this.validated=false;
        
        User.updateAccountStatus(this);
    }
    
    public static void updateAccountStatus(User user)
    {
    	if(user.isEnabled())
    	{
    		if(user.isValidated())
    		{
    			user.setAccountStatus(2);
    			
    		}else {
    			user.setAccountStatus(1);
    			
    		}
    		
    		
    	}else {
    		
    		user.setAccountStatus(0);
    	}
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
