package pmb.pmb.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pmb.pmb.model.User;
import pmb.pmb.model.UserAccountInformations;

@Repository
public interface UserAccountInfomationsRepository extends JpaRepository<UserAccountInformations, Long>  {

}
