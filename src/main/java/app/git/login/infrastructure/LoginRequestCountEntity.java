package app.git.login.infrastructure;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "login_request_count")
@NoArgsConstructor
@Getter
@ToString
public class LoginRequestCountEntity {

    @Id
    @Column(name = "login")
    private String login;

    @Column(name = "request_count", nullable = false)
    private Long requestCount;

    @Version
    @Setter
    private Integer version;

    public LoginRequestCountEntity(String login, Long requestCount) {
        this.login = login;
        this.requestCount = requestCount;
    }

    public void increaseRequestCounter() {
        requestCount++;
    }
}
