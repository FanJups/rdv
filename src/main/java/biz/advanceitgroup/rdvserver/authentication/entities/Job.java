package biz.advanceitgroup.rdvserver.authentication.entities;

import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Job {
	
	
	@Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
	private long jobID;
	
	
	@JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryID",referencedColumnName="categoryID")
	private Category categoryID;
	
	@JsonBackReference
    @OneToMany(mappedBy = "jobID",fetch = FetchType.LAZY)
    private Set<Bid> bids;
	
	@JsonBackReference
    @OneToMany(mappedBy = "jobID",fetch = FetchType.LAZY)
    private Set<File> files;
	
	
	/*
	 atus int [ 0=WAITING, 1=VALIDATED,
BID_ACCEPTED, 2=ATTRIBUTED, 3= DONE ,
4=CANCELLED
	 */
	private int jobStatus;
	
	
	private boolean isPublicJob;
	
	private boolean isClosedJob;
	
	private boolean isConflictedJob;
	
	private boolean isReportedAsAbusedJob;
	
	
	@Column(length= 100)
	private String jobTitle;
	
	@Column(length= 500)
	private String jobDescription;
	
	
	@Column(length= 500)
	private String jobAddress;
	
	private double jobPaymentAmount;
	
	private double minOfferAmount ;
	
	private double maxOfferAmount ;
	
	private Timestamp startDate;
	
	private Timestamp endDate;
	
	private int validityPeriod;
	
	@Column(length= 500)
	private String videoLink;
	
	private boolean requireInsurance;
	
	
	@Column(length= 5000)
	private String conflictReason;
	
	private long  conflictInititorID;
	
	private Timestamp postDate;
	
	private Timestamp acceptedDate;
	
private Timestamp attributedDate;
	
	private Timestamp conflictedDate;
	
	
private Timestamp closedDate;
	
	private Timestamp cancelledDate;
	
	private long attributedBidID;
	
	private long acceptedBidID;
	
	@Column(length= 7)
	private String workerValidationKeyPart;
	
	
	
	@Column(length= 7)
	private String providerValidationKeyPart;
	
	private long jobViewCount;
	
	private long jobBidCount;
	
	private long abuseReportCount;
	
	private double jobAddressLongitude;
	
	private double jobAddressLatitude;
	
	private Timestamp createDate;
	
	private Timestamp updateDate;
	
	@Column(length= 100)
    private String createUserName;
    
    
    @Column(length= 100)
    private String updateUserName;
    
   
	

}
