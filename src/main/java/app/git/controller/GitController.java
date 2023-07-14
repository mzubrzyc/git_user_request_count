package app.git.controller;

import app.git.common.Login;
import app.git.exceptions.ServerIsBusyException;
import app.git.login.LoginFacade;
import io.github.bucket4j.Bucket;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/")
@Slf4j
public class GitController {

    private final LoginFacade loginFacade;
    private static final Bucket bucket = BucketInitializer.initGitBucket();

    @GetMapping(path = "users/{login}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserGitInfoResponse> getLoginGitInfo(@PathVariable String login) throws IOException, InterruptedException, ServerIsBusyException {
        if (!bucket.tryConsume(1L)){
            throw new ServerIsBusyException("Too many requests, try again in a few seconds");
        }
        UserGitInfoResponse userGitInfoResponse = loginFacade.getUserGitInfo(new Login(login));
        return ResponseEntity.ok(userGitInfoResponse);
    }

}
