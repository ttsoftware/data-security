package shared.exception;

import java.security.GeneralSecurityException;

public class UserAuthenticationException extends GeneralSecurityException {

    public UserAuthenticationException() {
        super();
    }

    public UserAuthenticationException(String message) {
        super(message);
    }

    public UserAuthenticationException(Throwable cause) {
        super(cause);
    }
}
