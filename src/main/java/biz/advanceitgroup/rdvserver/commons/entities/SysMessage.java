package biz.advanceitgroup.rdvserver.commons.entities;

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
 * Messages du système,
par type et par langue
 * 
 * */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysMessage {
	
	@Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
	private long sysMessageID;
	
	
	@Column(nullable = false)
	private int msgType;
	
	@Column(nullable = false)
	private int msgCode;
	
	@Column(length=1000,nullable = false)
	private String msgValue_En;
	
	@Column(length=1000,nullable = false)
	private String msgValue_Fr;
	
}
