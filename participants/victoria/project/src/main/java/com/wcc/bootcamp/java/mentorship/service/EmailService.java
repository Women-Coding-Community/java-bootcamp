package com.wcc.bootcamp.java.mentorship.service;

import com.wcc.bootcamp.java.mentorship.model.Match;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Service for sending email notifications.
 * Uses JavaMailSender to send emails via configured SMTP server.
 * For local development, use MailHog (localhost:1025).
 */
@Service
public class EmailService {
    private static final Logger log = LoggerFactory.getLogger(EmailService.class);
    
    private final JavaMailSender mailSender;
    
    @Value("${spring.mail.from:noreply@mentorship-matcher.local}")
    private String fromAddress;
    
    @Value("${spring.mail.enabled:true}")
    private boolean emailEnabled;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * Sends match notification emails to both mentor and mentee.
     * Runs asynchronously to avoid blocking the main request.
     */
    @Async
    public void sendMatchNotification(Match match) {
        if (!emailEnabled) {
            log.info("Email disabled - would have sent match notification for match {}", match.getId());
            return;
        }
        
        sendMentorNotification(match);
        sendMenteeNotification(match);
    }

    private void sendMentorNotification(Match match) {
        String mentorEmail = match.getMentor().getEmail();
        String menteeName = match.getMentee().getName();
        String matchedSkills = String.join(", ", match.getMatchedSkills());
        int matchPercentage = (int) (match.getMatchScore() * 100);
        
        String subject = "New Mentee Match - " + menteeName;
        String body = String.format("""
            Hello %s,
            
            Great news! You have been matched with a new mentee.
            
            Mentee Details:
            - Name: %s
            - Email: %s
            - Matched Skills: %s
            - Compatibility Score: %d%%
            
            Please reach out to your mentee to schedule your first session.
            
            Best regards,
            Mentorship Matcher
            """,
            match.getMentor().getName(),
            menteeName,
            match.getMentee().getEmail(),
            matchedSkills.isEmpty() ? "General mentorship" : matchedSkills,
            matchPercentage
        );
        
        sendEmail(mentorEmail, subject, body);
    }

    private void sendMenteeNotification(Match match) {
        String menteeEmail = match.getMentee().getEmail();
        String mentorName = match.getMentor().getName();
        String matchedSkills = String.join(", ", match.getMatchedSkills());
        int matchPercentage = (int) (match.getMatchScore() * 100);
        
        String subject = "Mentor Match Found - " + mentorName;
        String body = String.format("""
            Hello %s,
            
            Congratulations! You have been matched with a mentor.
            
            Mentor Details:
            - Name: %s
            - Email: %s
            - Expertise: %s
            - Compatibility Score: %d%%
            
            Your mentor will reach out to you soon to schedule your first session.
            
            Best regards,
            Mentorship Matcher
            """,
            match.getMentee().getName(),
            mentorName,
            match.getMentor().getEmail(),
            String.join(", ", match.getMentor().getExpertiseAreas()),
            matchPercentage
        );
        
        sendEmail(menteeEmail, subject, body);
    }

    private void sendEmail(String to, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromAddress);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);
            
            mailSender.send(message);
            log.info("Email sent successfully to {}", to);
        } catch (MailException e) {
            log.error("Failed to send email to {}: {}", to, e.getMessage());
            // Don't throw - email failure shouldn't break the match creation
        }
    }
}
