package app.git.login.infrastructure;

import app.git.common.Login;
import app.git.login.LoginRequestCountPort;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.LockModeType;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.threads.TaskQueue;

@Slf4j
public class LoginRequestCountAdapter implements LoginRequestCountPort {

    private final LoginRequestCountDao loginRequestCountDao;
    private final EntityManager em;
    private static final ExecutorService executorService = new ThreadPoolExecutor(1, 1, 0, TimeUnit.MILLISECONDS, new TaskQueue());

    public LoginRequestCountAdapter(LoginRequestCountDao loginRequestCountDao, EntityManagerFactory entityManagerFactory) {
        this.loginRequestCountDao = loginRequestCountDao;
        this.em = entityManagerFactory.createEntityManager();
    }

    @Override
    public void updateRequestCountForLogin(Login login) {
        executorService.submit(() -> {
            EntityTransaction transaction = em.getTransaction();
            try {
                transaction.begin();
                LoginRequestCountEntity requestCountEntity = loginRequestCountDao.findRequestCountEntity(em, login);
                if (requestCountEntity == null) {
                    loginRequestCountDao.insertRequestCountForLogin(em, login);
                } else {
                    em.lock(requestCountEntity, LockModeType.OPTIMISTIC);
                    requestCountEntity.increaseRequestCounter();
                }
                transaction.commit();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                transaction.rollback();
                throw e;
            }
        });
    }
}
