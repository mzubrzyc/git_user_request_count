package app.git.login;

import app.git.common.Login;

import java.io.IOException;

public interface GitApiPort {

    UserGitInfo getUserGitInfoForLogin(Login login) throws IOException, InterruptedException;

}
