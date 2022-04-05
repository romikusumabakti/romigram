package romikusumabakti.romigram.repository;

import romikusumabakti.romigram.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findByUsernameOrEmail(String username, String email);

}
