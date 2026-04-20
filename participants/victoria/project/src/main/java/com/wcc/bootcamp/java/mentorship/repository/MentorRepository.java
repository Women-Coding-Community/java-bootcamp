package com.wcc.bootcamp.java.mentorship.repository;

import com.wcc.bootcamp.java.mentorship.model.Mentor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * JPA Repository for Mentor entity persistence.
 */
@Repository
public interface MentorRepository extends JpaRepository<Mentor, String> {
    
    Optional<Mentor> findByNameIgnoreCase(String name);
    
    Optional<Mentor> findByEmailIgnoreCase(String email);
}
