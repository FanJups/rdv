package biz.advanceitgroup.rdvserver.authentication.exceptions;

@SuppressWarnings("serial")
public class EmailDoesntExistException extends RuntimeException {
	
	public EmailDoesntExistException() {
        super();
    }

    public EmailDoesntExistException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public EmailDoesntExistException(final String message) {
        super(message);
    }

    public EmailDoesntExistException(final Throwable cause) {
        super(cause);
    }


}
