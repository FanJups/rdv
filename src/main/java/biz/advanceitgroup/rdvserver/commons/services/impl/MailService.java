package biz.advanceitgroup.rdvserver.commons.services.impl;

import biz.advanceitgroup.rdvserver.authentication.entities.User;
import org.apache.commons.lang.CharEncoding;
import org.picocontainer.annotations.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import javax.mail.internet.MimeMessage;
import java.util.Locale;

/**
 * Copyright (c) 2020, Advance-IT-Group, All Right Reserved.
 * https://advance-it-group.biz
 *
 * @author "Emmeni Emmanuel <emmanuel.emmeni@advance-it-group.biz>"
 * @since 01/02/2020 15:18
 */
@Service
public class MailService {

    private final Logger log = LoggerFactory.getLogger(MailService.class);

    private static final String USER = "user";
    private static final String BASE_URL = "baseUrl";

    @Inject
    private JavaMailSenderImpl javaMailSender;

    @Autowired
    private MessageSource messageSource;

    /*@Autowired
    private RdvNowProperties rdvNowProperties;

    @Autowired
    private SpringTemplateEngine templateEngine;*/

    @Async
    public void sendEmail(String to, String subject, String content, boolean isMultipart, boolean isHtml) {
        log.debug("Send e-mail[multipart '{}' and html '{}'] to '{}' with subject '{}' and content={}",
                isMultipart, isHtml, to, subject, content);

        // Prepare message using a Spring helper
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, CharEncoding.UTF_8);
            message.setTo(to);
            // --- message.setFrom(rdvNowProperties.getMail().getFrom());
            message.setSubject(subject);
            message.setText(content, isHtml);
            javaMailSender.send(mimeMessage);
            log.debug("Sent e-mail to User '{}'", to);
        } catch (Exception e) {
            log.warn("E-mail could not be sent to user '{}', exception is: {}", to, e.getMessage());
        }
    }

    @Async
    public void sendCreationEmail(User user, String baseUrl, String langKey) {
        log.debug("Sending creation e-mail to '{}'", user.getEmail());
        Locale locale = Locale.forLanguageTag(langKey);
        Context context = new Context(locale);
        context.setVariable(USER, user);
        context.setVariable(BASE_URL, baseUrl);
        // --- String content = templateEngine.process("creationEmail", context);
        String subject = messageSource.getMessage("email.activation.title", null, locale);
        // --- sendEmail(user.getEmail(), subject, content, false, true);
    }

    @Async
    public void sendInvitationMail(User user, String baseUrl, String langKey) {
        log.debug("Sending invitation e-mail to '{}'", user.getEmail());
        Locale locale = Locale.forLanguageTag(langKey);
        Context context = new Context(locale);
        context.setVariable(USER, user);
        context.setVariable(BASE_URL, baseUrl);
        // --- String content = templateEngine.process("invitationEmail", context);
        String subject = messageSource.getMessage("email.invitation.title", null, locale);
        // --- sendEmail(user.getEmail(), subject, content, false, true);
    }

    @Async
    public void sendPasswordResetMail(User user, String baseUrl, String langKey) {
        log.debug("Sending password reset e-mail to '{}'", user.getEmail());
        Locale locale = Locale.forLanguageTag(langKey);
        Context context = new Context(locale);
        context.setVariable(USER, user);
        context.setVariable(BASE_URL, baseUrl);
        // --- String content = templateEngine.process("passwordResetEmail", context);
        String subject = messageSource.getMessage("email.reset.title", null, locale);
        // --- sendEmail(user.getEmail(), subject, content, false, true);
    }

    @Async
    public void sendJobRequestCloseMail(User user, String baseUrl, String jobKey, String langKey) {
        log.debug("Sending invitation e-mail to '{}'", user.getEmail());
        Locale locale = Locale.forLanguageTag(langKey);
        Context context = new Context(locale);
        context.setVariable(USER, user);
        context.setVariable(BASE_URL, baseUrl);
        // --- String content = templateEngine.process("invitationEmail", context);
        String subject = messageSource.getMessage("email.invitation.title", null, locale);
        // --- sendEmail(user.getEmail(), subject, content, false, true);
    }
}

