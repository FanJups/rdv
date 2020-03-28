package biz.advanceitgroup.rdvserver.jobs.entities;

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

/**
 * 
 * 
 * Documents de preuve
sur les catégories
professionnelles
 * */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor


public class CategoryProofDocument {
	
	
	@Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
	private long categoryProofDocumentID;
	
	@JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryID",referencedColumnName="categoryID")
	private Category categoryID;
	
	@JsonBackReference
    @OneToMany(mappedBy = "categoryProofDocumentID",fetch = FetchType.LAZY)
    private Set<UserCategoryProofDocument> userCategoryProofDocuments;

	
	@Column(length= 50)
	private String docTitle_En;
	@Column(length= 500)
	private String docDescription_En;
	@Column(length= 50)
	private String docTitle_Fr;
	@Column(length= 50)
	private String docDescription_Fr;
	
    private Timestamp createDate;
	
	private Timestamp updateDate;
	

	@Column(length= 100)
    private String createUserName;
    
    
    @Column(length= 100)
    private String updateUserName;
    
    
    
    

	

}
