package app.git.login;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.verify;

import app.git.api.GitApiAdapter;
import app.git.common.Login;
import app.git.login.fixture.LoginDbProvider;
import app.git.login.fixture.LoginExampleDataProvider;
import app.git.util.UserGitInfoMapper;
import app.test.config.IntegrationTest;
import java.net.URI;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.util.DefaultUriBuilderFactory;

@IntegrationTest
class LoginFacadeTest {

    @LocalServerPort
    int serverPort;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private GitApiAdapter gitApiAdapter;
    @Autowired
    private LoginDbProvider loginDbProvider;

        @BeforeEach
        void before() {
            loginDbProvider.deleteAll();
        }

    @Test
    @SneakyThrows
    void getUserGitInfo() {
        // given
        Login login = new Login("octocat");
        given(gitApiAdapter.getUserGitInfoForLogin(login))
            .willReturn(UserGitInfoMapper.getInstance().toUserGitInfoRequest(LoginExampleDataProvider.OCTOCAT_GIT_INFO, login));
        // when
        MvcResult mvcResult = mockMvc
            .perform(
                MockMvcRequestBuilders
                    .get(buildGetLoginGitInfoUri(login))
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
            )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn();
        // then
        ArgumentCaptor<Login> LoginCaptor = ArgumentCaptor.forClass(Login.class);
        verify(gitApiAdapter).getUserGitInfoForLogin(LoginCaptor.capture());
        assertThat(LoginCaptor.getValue()).isEqualTo(login);
        LoginRequestCount requestCountForLogin = loginDbProvider.getRequestCountForLogin(login);
        assertThat(requestCountForLogin).isEqualTo(new LoginRequestCount(login, 1L));
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