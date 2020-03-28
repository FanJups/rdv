package biz.advanceitgroup.rdvserver.commons.entities;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/*

Template d
état
imprimable
* 
* */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportTemplate {
	
	
	@Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
	private long reportTemplateID;
	
	
	@Column(length= 50)
	private String code;
	
	
	@Column(length= 100)
	private String  name_En;
	
	@Column(length= 100)
	private String  name_Fr;
	
	@Column(length= 100)
	private String  description_En;
	
	@Column(length= 100)
	private String  description_Fr;
	
	@Column(length= 500)
	private String path;
	
	
	
	private Timestamp createDate;
	
	private Timestamp updateDate;
	
	@Column(length= 100)
    private String createUserName;
    
    
    @Column(length= 100)
    private String updateUserName;
	

}
