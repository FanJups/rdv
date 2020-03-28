package biz.advanceitgroup.rdvserver.commons.entities;

import org.springframework.http.HttpStatus;


import lombok.Data;

@Data


public class ApiResponse {   
	
	private String status;
	private String message;
	private Object object;
	private HttpStatus httpStatus;
	private AuthResponse authResponse;
	
	public ApiResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }
	
	public ApiResponse(String status, String message,Object object) {
        this.status = status;
        this.message = message;
        this.object = object;
    }
	
	public ApiResponse(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
	
	public ApiResponse(HttpStatus httpStatus, String message,Object object) {
        this.httpStatus = httpStatus;
        this.message = message;
        this.object = object;
    }
	
	public ApiResponse(HttpStatus httpStatus,String status, String message,Object object) {
        this.httpStatus = httpStatus;
        this.status = status;
        this.message = message;
        this.object = object;
    }
	
	public ApiResponse(HttpStatus httpStatus,String status, String message) {
        this.httpStatus = httpStatus;
        this.status = status;
        this.message = message;
        
    }
	
	public ApiResponse(String message) {
        
        this.message = message;
    }
	
	
	


}
