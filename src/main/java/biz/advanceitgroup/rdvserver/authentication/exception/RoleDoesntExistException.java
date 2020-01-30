package biz.advanceitgroup.rdvserver.authentication.exception;

@SuppressWarnings("serial")
public class RoleDoesntExistException extends RuntimeException {
	
	public RoleDoesntExistException() {
        super();
    }

    public RoleDoesntExistException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public RoleDoesntExistException(final String message) {
        super(message);
    }

    public RoleDoesntExistException(final Throwable cause) {
        super(cause);
    }

}
