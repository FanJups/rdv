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
 * Messages
échangés
dans le cadre d un job
 * 
 * */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
	
	
	@Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
	private long jobID;
	
	private long senderID;
	private long recipientID;
	
	
	@Column(length=5000)
	private String message;
	
	private Timestamp sentDate;
	
	private Timestamp readDate;
	
	private Timestamp replyDate;
	
	private Timestamp deleteDate;
	
	
	//[ 0=Sent , 1=Read , 2=Replied]
	private int status;
	
	private boolean isDeleted;
	
	private boolean isAdminMessage;
	
	private Timestamp createDate;
	
	private Timestamp updateDate;
	

	@Column(length= 100)
    private String createUserName;
    
    
    @Column(length= 100)
    private String updateUserName;
	
	

}
