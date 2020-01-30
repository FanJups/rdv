package biz.advanceitgroup.rdvserver.authentication.services.interfaces;

public interface SecurityService {
	
	String findLoggedInUsername();

    void autoLogin(String username, String password);

}
