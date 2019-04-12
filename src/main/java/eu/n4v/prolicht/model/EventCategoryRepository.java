package eu.n4v.prolicht.model;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EventCategoryRepository extends JpaRepository<EventCategory, Long> {

    @Override
    @Query
    public List<EventCategory> findAll();
}
