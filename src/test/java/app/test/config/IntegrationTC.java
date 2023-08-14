package app.test.config;

import app.git.App;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest(
    classes = App.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ComponentScan({"app"})
@ActiveProfiles(value = "test")
@AutoConfigureMockMvc
@ContextConfiguration(initializers = PostgresContainerInitializer.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface IntegrationTC {
}
