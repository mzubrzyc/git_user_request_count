package app.git.login;

import app.git.common.Login;
import app.git.controller.UserGitInfoResponse;
import lombok.AllArgsConstructor;

import java.io.IOException;

@AllArgsConstructor
public class LoginFacade {

    private final LoginService loginService;

    public UserGitInfoResponse getUserGitInfo(Login login) throws IOException, InterruptedException {
        return loginService.getUserGitInfo(login);
    }

}
