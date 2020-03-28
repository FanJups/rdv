package biz.advanceitgroup.rdvserver.authentication.repositories;

import java.util.Date;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import biz.advanceitgroup.rdvserver.authentication.entities.ForgottenPasswordToken;
import biz.advanceitgroup.rdvserver.authentication.entities.User;


public interface ForgottenPasswordTokenRepository extends JpaRepository<ForgottenPasswordToken, Long> {

	ForgottenPasswordToken findByToken(String token);

	ForgottenPasswordToken findByUser(User user);

    Stream<ForgottenPasswordToken> findAllByExpiryDateLessThan(Date now);

    void deleteByExpiryDateLessThan(Date now);

    @Modifying
    @Query("delete from ForgottenPasswordToken t where t.expiryDate <= ?1")
    void deleteAllExpiredSince(Date now);

}
