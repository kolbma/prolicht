package eu.n4v.prolicht.model;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EventRepository extends JpaRepository<Event, Long> {

    @Override
    @Query
    public List<Event> findAll();

    public List<Event> findByEventCategoryId(Long eventCategoryId);
}
