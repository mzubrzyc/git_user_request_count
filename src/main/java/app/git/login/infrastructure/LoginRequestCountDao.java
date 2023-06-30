package app.git.login.infrastructure;

import app.git.common.Login;
import jakarta.persistence.EntityManager;

public class LoginRequestCountDao {

    public LoginRequestCountEntity findRequestCountEntity(EntityManager em, Login login) {
        return em.find(LoginRequestCountEntity.class, login.value());
    }

    public void insertRequestCountForLogin(EntityManager em, Login login) {
        em.persist(new LoginRequestCountEntity(login.value(), 1L));
    }

}
