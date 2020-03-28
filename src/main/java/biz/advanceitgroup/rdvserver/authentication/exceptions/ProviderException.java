package biz.advanceitgroup.rdvserver.authentication.exceptions;

@SuppressWarnings("serial")
public class ProviderException extends RuntimeException {
	
	public ProviderException() {
        super();
    }

    public ProviderException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ProviderException(final String message) {
        super(message);
    }

    public ProviderException(final Throwable cause) {
        super(cause);
    }

}
