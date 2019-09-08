package NeuroActivity.letstalk.repository;

import NeuroActivity.letstalk.domain.Think;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ThinkRepo extends JpaRepository<Think, Long> {
    @EntityGraph(attributePaths = { "comments" })
    Page<Think> findAll(Pageable pageable);
}