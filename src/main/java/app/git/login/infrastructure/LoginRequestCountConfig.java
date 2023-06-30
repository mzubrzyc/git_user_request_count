package app.git.login.infrastructure;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoginRequestCountConfig {

    @Bean
    public LoginRequestCountDao loginRequestCountDao() {
        return new LoginRequestCountDao();
    }

    @Bean
    public LoginRequestCountAdapter loginJpaRepositoryAdapter(EntityManagerFactory entityManagerFactory, LoginRequestCountDao loginRequestCountDao) {
        return new LoginRequestCountAdapter(loginRequestCountDao, entityManagerFactory);
    }

}
