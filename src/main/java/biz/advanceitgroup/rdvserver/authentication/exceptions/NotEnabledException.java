package biz.advanceitgroup.rdvserver.authentication.exceptions;

@SuppressWarnings("serial")
public class NotEnabledException extends RuntimeException {
	
	public NotEnabledException() {
        super();
    }

    public NotEnabledException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public NotEnabledException(final String message) {
        super(message);
    }

    public NotEnabledException(final Throwable cause) {
        super(cause);
    }


}
