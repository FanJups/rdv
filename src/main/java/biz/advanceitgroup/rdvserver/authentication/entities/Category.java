package biz.advanceitgroup.rdvserver.authentication.entities;

import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * 
 * Catégories
professionnelles des
utilisateurs
 * */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Category {
	
	
	@Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
	private long categoryID;
	
	@JsonBackReference
    @OneToMany(mappedBy = "categoryID",fetch = FetchType.LAZY)
    private Set<Job> jobs;
	
	@Column(length= 50)
	private String name_En;
	@Column(length= 500)
	private String description_En;
	@Column(length= 50)
	private String name_Fr;
	@Column(length= 50)
	private String description_Fr;
	
	private boolean requireEval;
	
    private Timestamp createDate;
	
	private Timestamp updateDate;
	

	@Column(length= 100)
    private String createUserName;
    
    
    @Column(length= 100)
    private String updateUserName;
    
    @ManyToMany(mappedBy = "categories",fetch = FetchType.LAZY)
    @JsonBackReference // // Avoiding this error Expected ',' instead of ''
    private Set<User> users;
    
   
    @JsonBackReference
    @OneToMany(mappedBy = "categoryID",fetch = FetchType.LAZY)
    private Set<Quizz> quizzs;
    
    
    
    @JsonBackReference
    @OneToMany(mappedBy = "categoryID",fetch = FetchType.LAZY)
    private Set<CategoryProofDocument> categoryProofDocuments;


	
	

}
