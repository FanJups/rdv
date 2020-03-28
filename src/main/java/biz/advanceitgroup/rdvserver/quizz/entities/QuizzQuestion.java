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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * 
 * Question d
un jeu de
Quizz
 * 
 * */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizzQuestion {
	
	@Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
	
	private long quizzQuestionID;
	
	
	
	@JsonManagedReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "quizzID",referencedColumnName="quizzID")
	private Quizz quizzID;
	
	
	@JsonBackReference
	@OneToMany(mappedBy = "quizzQuestionID",fetch = FetchType.LAZY)
	private Set<QuizzQuestionResponse> quizzQuestionResponses;
	
	
	@Column(length=500)
	private String question_En;
	
	
	@Column(length=500)
	private String question_Fr;
	
	private int questionNumber;
	
    private Timestamp createDate;
	
	private Timestamp updateDate;
	

	@Column(length= 100)
    private String createUserName;
    
    
    @Column(length= 100)
    private String updateUserName;
	
	
}
