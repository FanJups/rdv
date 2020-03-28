package biz.advanceitgroup.rdvserver.commons.services.impl;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

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

import org.springframework.stereotype.Service;

@Service
public class SendingMail {
	
	private  String mailFrom="springboot.test.envoi.mail@gmail.com";
	
	private  String passwordMailFrom="TESTENVOIMAIL";
	
	
	
	public void send(String recipientAddress,
			String subject,String mailContent)throws AddressException , MessagingException , IOException
	{

		   Properties props = new Properties();
		   props.put("mail.smtp.auth", "true");
		   props.put("mail.smtp.starttls.enable", "true");
		   props.put("mail.smtp.host", "smtp.gmail.com");
		   props.put("mail.smtp.port", "587");
		   props.put("mail.debug", "true");
		   //props.put("mail.smtp.ssl.protocols", "TLSv1.2");            
		   //props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		   
		   Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			      protected PasswordAuthentication getPasswordAuthentication() {
			         return new PasswordAuthentication(mailFrom, passwordMailFrom);
			      }
			   });
			   Message msg = new MimeMessage(session);
			   
			   
			   msg.setFrom(new InternetAddress(mailFrom, false));

			   msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientAddress));
			   msg.setSubject(subject);
			   
			   msg.setSentDate(new Date());
			   
			   MimeBodyPart messageBodyPart = new MimeBodyPart();
			   
			   messageBodyPart.setContent(mailContent, "text/html");

			   Multipart multipart = new MimeMultipart();
			   multipart.addBodyPart(messageBodyPart);
			   //MimeBodyPart attachPart = new MimeBodyPart();

			   //attachPart.attachFile("/var/tmp/image19.png");
			   //multipart.addBodyPart(attachPart);
			   msg.setContent(multipart);
			   Transport.send(msg);   



	}




}
