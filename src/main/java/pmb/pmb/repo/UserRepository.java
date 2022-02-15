package pmb.pmb.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pmb.pmb.model.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmail(String email);

	boolean existsByEmail(String email);
	
	@Query("Select u from User u WHERE  u.email = :em")
	User foundByEmail(@Param("em")String email);

	@Query("Select u from User u WHERE u.userAccountInformations.accountReferenceTransaction = :accountReferenceTransaction")
	User findByUserReferenceTransaction(@Param("accountReferenceTransaction")String accountReferenceTransaction);

	List<User> findAll();
}
