package app.git.login;

import app.git.common.Login;

public interface LoginRequestCountPort {

    void updateRequestCountForLogin(Login login);
}
