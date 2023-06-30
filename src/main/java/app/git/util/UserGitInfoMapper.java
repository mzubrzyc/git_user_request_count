package app.git.util;

import app.git.common.Login;
import app.git.controller.UserGitInfoResponse;
import app.git.login.UserGitInfo;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserGitInfoMapper {

    private final JsonMapper jsonMapper = new JsonMapper();
    private static final UserGitInfoMapper INSTANCE = new UserGitInfoMapper();

    private UserGitInfoMapper() {
        config();
    }

    public static UserGitInfoMapper getInstance() {
        return INSTANCE;
    }

    private void config() {
        jsonMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        jsonMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public UserGitInfoResponse toUserGitInfoResponse(UserGitInfo userGitInfo, double calculation) {
        return new UserGitInfoResponse(
            userGitInfo.getId(),
            userGitInfo.getLogin(),
            userGitInfo.getName(),
            userGitInfo.getType(),
            userGitInfo.getAvatarUrl(),
            userGitInfo.getCreatedAt(),
            calculation
        );
    }

    public UserGitInfo toUserGitInfoRequest(String userGitInfoJson, Login login) throws JsonProcessingException {
        try {
            return jsonMapper.readValue(userGitInfoJson, UserGitInfo.class);
        } catch (JsonProcessingException e) {
            log.error("Error parsing userGitInfoJson for login {}", login.value());
            throw e;
        }
    }

}
