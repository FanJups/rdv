package biz.advanceitgroup.rdvserver.jobs.entities;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import biz.advanceitgroup.rdvserver.commons.entities.File;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * 
 * Documents de preuve
sur les c atégories
professionnelles d un
utilisateur
 * */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserCategoryProofDocument {
	
	
	@Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
	private long userCategoryProofDocumentID;
	
	@JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryProofDocumentID",referencedColumnName="categoryProofDocumentID")
	private CategoryProofDocument categoryProofDocumentID;
	
	/*
	 
	   User est lié à Category qui est lié à CategoryProofDocument qui est lié à UserCategoryProofDocument
	  
	 */
	
	
	//private long userID;
	
	
	//Unidirectionnal OneToOne because a file is not always a proofdocument
	
	@OneToOne
    @JoinColumn(name ="documentFileID")
	private File  documentFileID;
	
private Timestamp createDate;
	
	private Timestamp updateDate;
	

	@Column(length= 100)
    private String createUserName;
    
    
    @Column(length= 100)
    private String updateUserName;
	
	

}
