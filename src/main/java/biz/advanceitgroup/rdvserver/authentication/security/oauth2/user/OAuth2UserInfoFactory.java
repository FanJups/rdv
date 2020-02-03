package biz.advanceitgroup.rdvserver.authentication.security.oauth2.user;

import java.util.Map;

import biz.advanceitgroup.rdvserver.authentication.entities.AuthProvider;
import biz.advanceitgroup.rdvserver.authentication.exception.OAuth2AuthenticationProcessingException;

public class OAuth2UserInfoFactory {
	
	public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if(registrationId.equalsIgnoreCase(AuthProvider.google.toString())) {
            return new GoogleOAuth2UserInfo(attributes);
        } else if (registrationId.equalsIgnoreCase(AuthProvider.facebook.toString())) {
            return new FacebookOAuth2UserInfo(attributes);
        } else {
            throw new OAuth2AuthenticationProcessingException("Sorry! Login with " + registrationId + " is not supported yet.");
        }
    }

}
