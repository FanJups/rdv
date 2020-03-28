package biz.advanceitgroup.rdvserver.authentication.event.listener;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import biz.advanceitgroup.rdvserver.authentication.entities.User;
import biz.advanceitgroup.rdvserver.authentication.event.OnForgottenPasswordEvent;
import biz.advanceitgroup.rdvserver.authentication.services.interfaces.UserService;
import biz.advanceitgroup.rdvserver.commons.services.impl.SendingMail;

@Component
public class ForgottenPasswordListener implements ApplicationListener<OnForgottenPasswordEvent>{
	
	@Autowired
    private UserService service;
	
	@Autowired
    private SendingMail sendingMail;
	
	   @Override
	    public void onApplicationEvent(OnForgottenPasswordEvent event) {
	        try {
				this.forgottenPassword(event);
			} catch (AddressException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }

	
    private void forgottenPassword(OnForgottenPasswordEvent event) throws AddressException , MessagingException , IOException {
    	
    	
    	User user = event.getUser();
        String token = UUID.randomUUID().toString();
        service.createForgottenPasswordTokenForUser(user, token);
        
         
        String recipientAddress = user.getEmail();
        
        String subject = "Forgotten Password";
        String forgottenPassword 
          = event.getAppUrl() + "/user/resetPassword?token=" + token;
        
        String message = "You forgot password. We  send you this  message in order to reset your password : ";

    	 
        sendingMail.send(recipientAddress, subject, message + forgottenPassword);  
     
		
}


	
	

}
