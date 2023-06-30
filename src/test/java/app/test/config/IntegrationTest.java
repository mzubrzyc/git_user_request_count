package app.test.config;

import app.git.App;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes =   App.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ComponentScan({"app"})
@ActiveProfiles(value = "test")
@AutoConfigureMockMvc
@Retention(RetentionPolicy.RUNTIME)
public @interface IntegrationTest {
}
