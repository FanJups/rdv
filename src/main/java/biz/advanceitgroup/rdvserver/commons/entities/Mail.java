package biz.advanceitgroup.rdvserver.commons.entities;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class Mail {
	
    private String mailFrom;
    
    private String mailTo;
 
    private String mailCc;
 
    private String mailBcc;
 
    private String mailSubject;
 
    private String mailContent;
 
    private String contentType;
 
    private List < Object > attachments;
 
    public Mail() {
        this.contentType = "text/plain";
    }

}
