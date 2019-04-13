package eu.n4v.prolicht.model;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface HobbyRepository extends JpaRepository<Hobby, Long> {

    @Override
    @Query
    public List<Hobby> findAll();

    public List<Hobby> findByHobbyCategoryId(long hobbyCategoryId);
}
