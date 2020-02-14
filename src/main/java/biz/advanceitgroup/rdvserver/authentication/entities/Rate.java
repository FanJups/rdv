package biz.advanceitgroup.rdvserver.authentication.entities;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * 
 * Not
ation des
travailleurs et des
fournisseurs
 * 
 * */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Rate {
	
	
	@Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
	private long rateID;
	
	private long ratingUserID;
	
	private long ratedUserID;
	
	private long jobID;
	
	@Column(length= 500)
	private String comments;
	
	private Timestamp ratingDate;
	
	private double rate;
	
	
	   private Timestamp createDate;
		
		private Timestamp updateDate;
		

		@Column(length= 100)
	    private String createUserName;
	    
	    
	    @Column(length= 100)
	    private String updateUserName;

	
	

}
