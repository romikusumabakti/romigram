package romikusumabakti.romigram.repository;

import org.springframework.data.jpa.repository.Query;
import romikusumabakti.romigram.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findByUsernameOrEmail(String username, String email);

    @Query("SELECT a FROM Account a WHERE concat(' ',a.name) LIKE concat('% ',:keywords,'%') OR concat(' ',a.username) LIKE concat('% ',:keywords,'%')")
    List<Account> search(String keywords);

}
