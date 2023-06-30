package app.git.login;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import app.git.api.GitApiAdapter;
import app.git.common.Login;
import app.git.login.fixture.LoginDbProvider;
import app.git.login.fixture.LoginExampleDataProvider;
import app.git.util.UserGitInfoMapper;
import app.test.config.IntegrationTest;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.util.DefaultUriBuilderFactory;

@IntegrationTest
class LoginFacadeMultiThreadsTest {

    @LocalServerPort
    int serverPort;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private GitApiAdapter gitApiAdapter;
    @Autowired
    private LoginDbProvider loginDbProvider;

    @Test
    @SneakyThrows
    @DisplayName("Testing capabilities for multithreaded requests")
    void getUserGitInfoConcurrent() {
        // given
        Login login = new Login("octocat");
        given(gitApiAdapter.getUserGitInfoForLogin(login))
            .willReturn(UserGitInfoMapper.getInstance().toUserGitInfoRequest(LoginExampleDataProvider.OCTOCAT_GIT_INFO, login));
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        List<Callable<Integer>> callableTasks = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            callableTasks.add(prepareGitApiRequestCallable(login));
        }
        // when
        List<Future<Integer>> futures = executorService.invokeAll(callableTasks);
        // then
        int numberOfResponses = 0;
        for (Future<Integer> future : futures) {
            if (future.get() == 1) {
                numberOfResponses++;
            }
        }
        LoginRequestCount requestCountForLogin = loginDbProvider.getRequestCountForLogin(login);
        assertThat(requestCountForLogin.requestCount()).isEqualTo(numberOfResponses);
    }

    private Callable<Integer> prepareGitApiRequestCallable(Login login) {
        return () -> {
            MvcResult mvcResult;
            try {
                mvcResult = mockMvc
                    .perform(
                        MockMvcRequestBuilders
                            .get(buildGetLoginGitInfoUri(login))
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                    )
                    .andReturn();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            try {
                Exception resolvedException = mvcResult.getResolvedException();
                if (resolvedException != null) {
                    throw resolvedException;
                }
            } catch (Exception e) {
                return 0;
            }
            return 1;
        };
    }

    private URI buildGetLoginGitInfoUri(Login login) {
        return new DefaultUriBuilderFactory()
            .builder()
            .scheme("http")
            .host("localhost")
            .port(serverPort)
            .pathSegment("users", login.value())
            .build();
    }

}