package app.git.login;

import app.git.common.Login;

public record LoginRequestCount(Login login, long requestCount) {
}
