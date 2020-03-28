package biz.advanceitgroup.rdvserver.authentication.repositories;

import java.util.List;
import java.util.Optional;

import biz.advanceitgroup.rdvserver.authentication.security.AuthoritiesConstants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import biz.advanceitgroup.rdvserver.authentication.entities.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	Optional<User> findByEmail(String email);

    User findByNickName(String nickName);

    Boolean existsByEmail(String email);
    
    @Modifying
    @Query("update User u set u.paymentMode = ?1, u.paymentAccount = ?2 where u.id = ?3")
    void setPaymentInfosById(String paymentMode, String paymentAccount, long id);
    
    @Modifying
    @Query("update User u set u.firstName = ?1, u.lastName = ?2, u.nickName = ?3,"
    		+ " u.phoneNumber = ?4, u.personnalAddress = ?5, u.professionnalAddress = ?6,"
    		+ " u.activityRadius = ?7, u.businessDescription = ?8, u.paymentMode = ?9,"
    		+ " u.paymentAccount = ?10  where u.id = ?11")
    void updateProfileInformationById(String firstname, String lastname,String nickname, 
    		String phonenumber, String personnaladdress, String professionnaladdress,long activityradius,
    		String businessdescription,String paymentmode, String paymentaccount, long id);

    
    
    @Modifying
    @Query("update User u set u.encryptPwd = ?1 where u.email = ?2")
    void updatePassword(String newpassword , String email);
    
    @Query("SELECT u " +
            "FROM User as u " +
            "JOIN u.roles as ur " +
            "WHERE ur.name = " + AuthoritiesConstants.ROLE_ADMIN)
    List<User> findAllAdmin();

}