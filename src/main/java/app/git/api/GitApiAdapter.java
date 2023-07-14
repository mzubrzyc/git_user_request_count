package app.git.api;

import app.git.common.Login;
import app.git.login.GitApiPort;
import app.git.login.UserGitInfo;
import app.git.util.UserGitInfoMapper;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.springframework.web.util.UriTemplate;

public class GitApiAdapter implements GitApiPort {

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final UserGitInfoMapper userGitInfoMapper = UserGitInfoMapper.getInstance();
    private final String GIT_API_URL_USER_TEMPLATE;

    public GitApiAdapter(String baseGitApiUrl) {
        this.GIT_API_URL_USER_TEMPLATE = baseGitApiUrl;
    }

    @Override
    public UserGitInfo getUserGitInfoForLogin(Login login) throws IOException, InterruptedException {
        String userGitInfoJson = httpClient.send(
            prepareHttpRequest(login.value()),
            HttpResponse.BodyHandlers.ofString()
        ).body();
        return userGitInfoMapper.toUserGitInfoRequest(userGitInfoJson, login);
    }

    private HttpRequest prepareHttpRequest(String login) {
        UriTemplate gitUserUriTemplate = new UriTemplate(GIT_API_URL_USER_TEMPLATE);
        return HttpRequest.newBuilder()
                          .uri(gitUserUriTemplate.expand(login))
                          .GET()
                          .build();
    }

}
