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

/*
 
Fichier chargé ou
téléchargé.
* 
* */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class File {
	
	@Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
	private long fileID;
	
	
	/*
	 
	  [ 0=PROFILE_IMAGE,
1 =PERSONNAL_ADDRESS_PROOF, 2 =
PROFESSIONNAL_ADDRESS_PROOF ,
3=CATEGORY_PROOF_DOC, 4= JOB_IMAGE ,
5=BID_IMAGE]
	  
	 */
	private int docType;
	
	
	
	@JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userID",referencedColumnName="id")
	private User userID;
	
	
	
	@JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "jobID",referencedColumnName="jobID")
	private Job jobID;
	
	
	
	private long categoryProofDocumentID;
	
	
	@Column(length= 100)
	private String name;
	
	@Column(length= 500)
	private String fileURL;
	
	private Timestamp createDate;
	
	private Timestamp updateDate;
	
	@Column(length= 100)
    private String createUserName;
    
    
    @Column(length= 100)
    private String updateUserName;
	
	
}
