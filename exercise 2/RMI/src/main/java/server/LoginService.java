package server;

import javax.security.auth.login.LoginException;

public interface LoginService<T> {

    boolean login(T user) throws LoginException;
}
