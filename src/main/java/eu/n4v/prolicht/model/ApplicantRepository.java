package eu.n4v.prolicht.model;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicantRepository extends JpaRepository<Applicant, Long> {

    public Optional<Applicant> findFirstByOrderByIdAsc();

    public Optional<Applicant> findOneByAccount(Account account);
}
