package eu.n4v.prolicht.model;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface KnowledgeRepository extends JpaRepository<Knowledge, Long> {

    @Override
    @Query
    public List<Knowledge> findAll();

    public List<Knowledge> findByKnowledgeCategoryId(Long knowledgeCategoryId);
}
