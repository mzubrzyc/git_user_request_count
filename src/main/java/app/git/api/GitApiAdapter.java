package app.git.api;

import app.git.common.Login;
import app.git.login.GitApiPort;
import app.git.login.UserGitInfo;
import app.git.util.UserGitInfoMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GitApiAdapter implements GitApiPort {

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final UserGitInfoMapper userGitInfoMapper = UserGitInfoMapper.getInstance();
    private final String BASE_GIT_API_URL;

    public GitApiAdapter(String baseGitApiUrl) {
        this.BASE_GIT_API_URL = baseGitApiUrl;
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
        String baseGitUrl = BASE_GIT_API_URL + "users/";
        return HttpRequest.newBuilder()
                          .uri(URI.create(baseGitUrl.concat(login)))
                          .GET()
                          .build();
    }

    /*
     * todo: create utility to build URI
     * */
}
