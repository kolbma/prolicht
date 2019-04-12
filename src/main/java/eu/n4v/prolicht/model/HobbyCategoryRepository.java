package eu.n4v.prolicht.model;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface HobbyCategoryRepository extends JpaRepository<HobbyCategory, Long> {

    @Override
    @Query
    public List<HobbyCategory> findAll();
}
