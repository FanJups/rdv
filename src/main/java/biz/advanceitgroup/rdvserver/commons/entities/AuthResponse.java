package biz.advanceitgroup.rdvserver.commons.entities;

import java.util.Date;

import lombok.Data;

@Data
public class AuthResponse {
	
	 private String accessToken;
	    private String tokenType = "Bearer";
	    private long id;
	    private String role;
	    private Date expirationDate;
	    

	    public AuthResponse(String accessToken,long id,String role,Date expirationDate) {
	        this.accessToken = accessToken;
	        this.id = id;
	        this.role = role;
	        this.expirationDate = expirationDate;
	    }

	    


}
