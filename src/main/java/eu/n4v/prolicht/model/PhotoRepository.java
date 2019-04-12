package eu.n4v.prolicht.model;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<Photo, Long> {

    public List<Photo> findByApplicant(Applicant applicant);

    public List<Photo> findByApplicantId(Long applicantId);
}
