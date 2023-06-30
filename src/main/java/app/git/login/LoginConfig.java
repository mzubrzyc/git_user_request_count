package app.git.login;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoginConfig {

    @Bean
    public LoginFacade loginFacade(LoginService loginService) {
        return new LoginFacade(loginService);
    }

}
