package app.git.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GitApiConfig {

    private final String BASE_GIT_API_URL;

    public GitApiConfig(
        @Value("${base.git.api.url}") String baseGitApiUrl
    ) {
        this.BASE_GIT_API_URL = baseGitApiUrl;
    }

    @Bean
    public GitApiAdapter gitApiAdapter() {
        return new GitApiAdapter(BASE_GIT_API_URL);
    }

}
