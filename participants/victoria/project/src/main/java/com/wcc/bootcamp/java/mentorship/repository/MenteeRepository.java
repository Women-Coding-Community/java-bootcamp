package com.wcc.bootcamp.java.mentorship.repository;

import com.wcc.bootcamp.java.mentorship.model.Mentee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * JPA Repository for Mentee entity persistence.
 */
@Repository
public interface MenteeRepository extends JpaRepository<Mentee, String> {
    
    Optional<Mentee> findByNameIgnoreCase(String name);
    
    Optional<Mentee> findByEmailIgnoreCase(String email);
    
    java.util.List<Mentee> findByIsMatchedFalse();
}
