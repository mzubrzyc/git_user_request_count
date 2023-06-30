package app.git.login.fixture;

import app.git.common.Login;
import app.git.login.infrastructure.LoginRequestCountEntity;
import jakarta.persistence.LockModeType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRequestCountJpaRepository extends JpaRepository<LoginRequestCountEntity, Long> {

    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query("select l from LoginRequestCountEntity l where l.login = :#{#login.value()}")
    Optional<LoginRequestCountEntity> getLoginRequestCount(@Param("login") Login login);

    @Modifying
    @Query("delete from LoginRequestCountEntity")
    void deleteAll();

}
