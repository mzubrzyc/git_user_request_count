package app.git.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GitApiConfig {

    private final String GIT_API_URL_USER_TEMPLATE;

    public GitApiConfig(
        @Value("${git.api.url.user.template}") String gitApiUrlUserTemplate
    ) {
        this.GIT_API_URL_USER_TEMPLATE = gitApiUrlUserTemplate;
    }

    @Bean
    public GitApiAdapter gitApiAdapter() {
        return new GitApiAdapter(
            GIT_API_URL_USER_TEMPLATE
        );
    }

}
