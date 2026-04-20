-- Sample seed data for Mentorship Matcher
-- This file is automatically executed by Spring Boot on startup
-- Only inserts data if tables are empty

-- Sample Mentors (uncomment to use)
-- INSERT INTO mentors (id, name, email, max_mentees, current_mentee_count) 
-- SELECT 'mentor-1', 'Alice Johnson', 'alice@example.com', 3, 0
-- WHERE NOT EXISTS (SELECT 1 FROM mentors WHERE id = 'mentor-1');

-- INSERT INTO mentor_expertise (mentor_id, expertise) VALUES ('mentor-1', 'java');
-- INSERT INTO mentor_expertise (mentor_id, expertise) VALUES ('mentor-1', 'spring boot');
-- INSERT INTO mentor_expertise (mentor_id, expertise) VALUES ('mentor-1', 'sql');

-- Sample Mentees (uncomment to use)
-- INSERT INTO mentees (id, name, email, experience_level, is_matched)
-- SELECT 'mentee-1', 'Bob Smith', 'bob@example.com', 'beginner', false
-- WHERE NOT EXISTS (SELECT 1 FROM mentees WHERE id = 'mentee-1');

-- INSERT INTO mentee_goals (mentee_id, goal) VALUES ('mentee-1', 'java');
-- INSERT INTO mentee_goals (mentee_id, goal) VALUES ('mentee-1', 'spring boot');
