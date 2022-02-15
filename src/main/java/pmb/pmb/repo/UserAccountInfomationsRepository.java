package pmb.pmb.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pmb.pmb.model.User;
import pmb.pmb.model.UserAccountInformations;

@Repository
public interface UserAccountInfomationsRepository extends JpaRepository<UserAccountInformations, Long>  {

	UserAccountInformations findByAccountReferenceTransaction(String userGetter);

}
