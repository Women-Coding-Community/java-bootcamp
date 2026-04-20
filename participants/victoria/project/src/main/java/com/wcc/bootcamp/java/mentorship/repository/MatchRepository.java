package com.wcc.bootcamp.java.mentorship.repository;

import com.wcc.bootcamp.java.mentorship.model.Match;
import com.wcc.bootcamp.java.mentorship.model.Mentee;
import com.wcc.bootcamp.java.mentorship.model.Mentor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * JPA Repository for Match entity persistence.
 */
@Repository
public interface MatchRepository extends JpaRepository<Match, String> {
    
    List<Match> findByStatus(Match.MatchStatus status);
    
    List<Match> findByMentor(Mentor mentor);
    
    List<Match> findByMentee(Mentee mentee);
    
    List<Match> findByMentorAndStatus(Mentor mentor, Match.MatchStatus status);
    
    List<Match> findByMenteeAndStatus(Mentee mentee, Match.MatchStatus status);
    
    void deleteByMentee(Mentee mentee);
    
    void deleteByMentor(Mentor mentor);
}
