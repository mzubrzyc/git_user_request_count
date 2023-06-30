package app.git.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GitApiConfig {

    @Bean
    public GitApiAdapter gitApiAdapter() {
        return new GitApiAdapter();
    }

}
