package biz.advanceitgroup.rdvserver.authentication.event;

import java.util.Locale;

import org.springframework.context.ApplicationEvent;

import biz.advanceitgroup.rdvserver.authentication.entities.User;

@SuppressWarnings("serial")
public class OnForgottenPasswordEvent extends ApplicationEvent {
	
    private String appUrl;
    private Locale locale;
    private User user;
    
    public OnForgottenPasswordEvent(final User user, final Locale locale, final String appUrl) {
        super(user);
        this.user = user;
        this.locale = locale;
        this.appUrl = appUrl;
    }
    

    public String getAppUrl() {
        return appUrl;
    }

    public Locale getLocale() {
        return locale;
    }

    public User getUser() {
        return user;
    }


}
