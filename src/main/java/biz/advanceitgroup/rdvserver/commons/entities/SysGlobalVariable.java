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
 *Variables Globales
du
système (Paramètres
globaux).
 * 
 * */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysGlobalVariable {
	
	@Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
	private long sysGlobalVariableID;
	
	
	
    @Column(length=5, nullable = false)
	private String varCode;
    
    @Column(length=5000, nullable = false)
	private String varValue;
    
    
    @Column(nullable = false)
	
    private int varType;
	
    
@Column(nullable = false)
	
    private int varVisible;
	
	

}
