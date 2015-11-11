package shared.exception;

import java.security.GeneralSecurityException;

public class UserPermissionException extends GeneralSecurityException {

    public UserPermissionException() {
        super();
    }

    public UserPermissionException(String message) {
        super(message);
    }

    public UserPermissionException(Throwable cause) {
        super(cause);
    }
}
