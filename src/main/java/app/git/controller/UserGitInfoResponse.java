package app.git.controller;

import app.git.login.UserGitInfo;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@JsonPropertyOrder({
    "id",
    "login",
    "name",
    "type",
    "avatarUrl",
    "createdAt",
    "calculations"
})
public class UserGitInfoResponse {
    private long id;
    private String login;
    private String name;
    private String type;
    private String avatarUrl;
    private String createdAt;
    private double calculations;

    public static UserGitInfoResponse fromUserGitInfo(UserGitInfo userGitInfo) {
        return new UserGitInfoResponse(
            userGitInfo.getId(),
            userGitInfo.getLogin(),
            userGitInfo.getName(),
            userGitInfo.getType(),
            userGitInfo.getAvatarUrl(),
            userGitInfo.getCreatedAt(),
            doCalculations(userGitInfo.getFollowers(), userGitInfo.getPublicRepos())
        );
    }

    private static double doCalculations(long followers, int publicRepos) {
        if (followers == 0) {
            return 0d;
        }
        return ((double) 6 / followers) * (2 + publicRepos);
    }

}
