package eu.n4v.prolicht.model;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

    public Optional<Account> findOneByUsername(String username);
}
