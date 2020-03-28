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
import biz.advanceitgroup.rdvserver.authentication.event.OnRegistrationCompleteEvent;
import biz.advanceitgroup.rdvserver.authentication.services.interfaces.UserService;
import biz.advanceitgroup.rdvserver.commons.services.impl.SendingMail;
import biz.advanceitgroup.rdvserver.configurations.AppProperties;


@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {


	@Autowired
    private UserService service;
	
	@Autowired
    private SendingMail sendingMail;
	
	
	
    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        try {
			this.confirmRegistration(event);
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
 
    
    
    private void confirmRegistration(OnRegistrationCompleteEvent event) throws AddressException , MessagingException , IOException {
    	
    	
    	User user = event.getUser();
        String token = UUID.randomUUID().toString();
        service.createVerificationTokenForUser(user, token);
        
         
        String recipientAddress = user.getEmail();
        
        String subject = "Registration Confirmation";
        String confirmationUrl 
          = event.getAppUrl() + "/user/activateEmailAccount?token=" + token;
        
        String message = "You registered successfully. We  send you a confirmation message to your email account. : ";

    	   

        sendingMail.send(recipientAddress, subject, message +confirmationUrl);  
        
 	
}



}
