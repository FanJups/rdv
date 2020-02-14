package biz.advanceitgroup.rdvserver.authentication.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import biz.advanceitgroup.rdvserver.authentication.entities.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	Optional<User> findByEmail(String email);
	
	

    Boolean existsByEmail(String email);
    
    @Modifying
    @Query("update User u set u.paymentMode = ?1, u.paymentAccount = ?2 where u.id = ?3")
    void setPaymentInfosById(String paymentMode, String paymentAccount, long id);
    
    @Modifying
    @Query("update User u set u.firstName = ?1, u.lastName = ?2, u.nickName = ?3,"
    		+ " u.phoneNumber = ?4, u.personnalAddress = ?5, u.professionnalAddress = ?6,"
    		+ " u.activityRadius = ?7, u.businessDescription = ?8, u.paymentMode = ?9,"
    		+ " u.paymentAccount = ?10  where u.id = ?11")
    void updateProfileInformationById(String firstName, String lastName,String nickName, 
    		String phoneNumber, String personnalAddress, String professionnalAddress,long activityRadius,
    		String businessDescription,String paymentMode, String paymentAccount, long id);

}