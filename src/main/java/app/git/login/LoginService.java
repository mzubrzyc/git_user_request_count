package app.git.login;

import app.git.common.Login;
import app.git.controller.UserGitInfoResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@AllArgsConstructor
@Slf4j
public class LoginService {

    private final LoginRequestCountPort loginRequestCountPort;
    private final GitApiPort gitApiPort;

    UserGitInfoResponse getUserGitInfo(Login login) throws IOException, InterruptedException {
        UserGitInfo userGitInfo = gitApiPort.getUserGitInfoForLogin(login);
        loginRequestCountPort.updateRequestCountForLogin(login);
        return UserGitInfoResponse.fromUserGitInfo(userGitInfo);
    }

}
