package app.git.login.fixture;

import app.git.common.Login;
import app.git.login.LoginRequestCount;
import app.git.login.infrastructure.LoginRequestCountEntity;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.transaction.annotation.Transactional;

@TestComponent
@Transactional
public class LoginDbProvider {

    @Autowired
    private LoginRequestCountJpaRepository loginRequestCountJpaRepository;

    public LoginRequestCount getRequestCountForLogin(Login login) {
        Optional<LoginRequestCountEntity> requestCountForLogin = loginRequestCountJpaRepository.getLoginRequestCount(login);
        return requestCountForLogin
            .map(loginRequestCountEntity -> new LoginRequestCount(login, loginRequestCountEntity.getRequestCount()))
            .orElseGet(() -> new LoginRequestCount(login, 0));
    }

    public void deleteAll() {
        loginRequestCountJpaRepository.deleteAll();
    }

}
