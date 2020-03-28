package biz.advanceitgroup.rdvserver.quizz.entities;


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

import biz.advanceitgroup.rdvserver.jobs.entities.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * 
 * Jeu de
Quizz
 * 
 * */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Quizz {
	
	
	@Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
	private long quizzID;
	
	
	@JsonManagedReference
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryID",referencedColumnName="categoryID")
	private Category categoryID;
	
	@Column(length=50)
	private String name_En;
	
	@Column(length=500)
	private String description_En;
	
	
	@Column(length=50)
	private String name_Fr;
	
	@Column(length=500)
	private String description_Fr;
	
	private int evalQuestionCount;
	
	private long evalDuration;
	
	private long  evalMinOKResponseCount;
	
    private Timestamp createDate;
	
	private Timestamp updateDate;
	

	@Column(length= 100)
    private String createUserName;
    
    
    @Column(length= 100)
    private String updateUserName;
    
    
    
    @JsonBackReference
    @OneToMany(mappedBy = "quizzID",fetch = FetchType.LAZY)
    private Set<QuizzQuestion> quizzQuestions;

}

