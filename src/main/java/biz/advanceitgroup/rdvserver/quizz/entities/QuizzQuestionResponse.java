package biz.advanceitgroup.rdvserver.quizz.entities;


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

Réponse à une
q uestion d un jeu de
Quizz
* 
* */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizzQuestionResponse {
	
	@Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
	private long quizzQuestionResponseID;
	
	
	
	@JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quizzQuestionID",referencedColumnName="quizzQuestionID")
	private QuizzQuestion quizzQuestionID;
	
	@Column(length=500)
	private String response_En;
	
	@Column(length=500)
	private String response_Fr;
	
	private int responseNumber;
	
	private boolean isCorrect;
	
private Timestamp createDate;
	
	private Timestamp updateDate;
	

	@Column(length= 100)
    private String createUserName;
    
    
    @Column(length= 100)
    private String updateUserName;
	


}

