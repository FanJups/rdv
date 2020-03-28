package biz.advanceitgroup.rdvserver.authentication.exceptions;

@SuppressWarnings("serial")
public class UpdatingSamePasswordException extends RuntimeException {
	
	public UpdatingSamePasswordException() {
        super();
    }

    public UpdatingSamePasswordException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public UpdatingSamePasswordException(final String message) {
        super(message);
    }

    public UpdatingSamePasswordException(final Throwable cause) {
        super(cause);
    }


}
