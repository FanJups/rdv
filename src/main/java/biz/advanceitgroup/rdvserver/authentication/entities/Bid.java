package biz.advanceitgroup.rdvserver.authentication.entities;


import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * 
 * Offres des travailleurs * 
 * */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bid {
	
	
	@Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
	private long bidID;
	
	@JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "jobID",referencedColumnName="jobID")
	private Job jobID;
	
	
	
	@Column(length = 500)
	private String bidDescription;
	
	private double bidOfferAmount;
	
	private Timestamp startDate;
	
	private Timestamp endDate;
	
	@Column(length= 500)
	private String videoLink;
	
	/*
	 bidStatus int [ 0=WAITING, 1= ACCEPTED,
2=ATTRIBUTED, 3= CLOSED ,
4=CANCELLED]
	 */
	private int bidStatus;
	
	private boolean isConflictedBid;
	
	private boolean isReportedAsAbuseBid;
	
	private Timestamp bidDate;
	
	private Timestamp acceptedDate;
	
private Timestamp attributedDate;
	
	private Timestamp conflictedDate;
	
private Timestamp closedDate;
	
	private Timestamp cancelledDate;
	
    private Timestamp createDate;
	
	private Timestamp updateDate;
	

	@Column(length= 100)
    private String createUserName;
    
    
    @Column(length= 100)
    private String updateUserName;
    
    
    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userID",referencedColumnName="id")
	private User userID;
    
    

	
}
