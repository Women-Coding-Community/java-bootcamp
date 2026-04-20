package com.wcc.bootcamp.java.mentorship.controller;

import com.wcc.bootcamp.java.mentorship.model.Match;
import com.wcc.bootcamp.java.mentorship.service.MentorshipService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller for match-related operations.
 */
@Controller
@RequestMapping("/matches")
public class MatchController {

    private final MentorshipService mentorshipService;

    public MatchController(MentorshipService mentorshipService) {
        this.mentorshipService = mentorshipService;
    }

    @GetMapping
    public String listMatches(Model model) {
        model.addAttribute("activeMatches", mentorshipService.getActiveMatches());
        model.addAttribute("allMatches", mentorshipService.getAllMatches());
        return "matches/list";
    }

    @GetMapping("/find")
    public String findMatches(Model model) {
        model.addAttribute("potentialMatches", mentorshipService.findAllPotentialMatches());
        model.addAttribute("mentees", mentorshipService.getAllMentees());
        model.addAttribute("mentors", mentorshipService.getAllMentors());
        return "matches/find";
    }

    @PostMapping("/create")
    public String createMatch(@RequestParam String mentorId,
                              @RequestParam String menteeId,
                              RedirectAttributes redirectAttributes) {
        try {
            Match match = mentorshipService.createMatch(mentorId, menteeId);
            redirectAttributes.addFlashAttribute("successMessage",
                    "Match created between " + match.getMentor().getName() + 
                    " and " + match.getMentee().getName() + "!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        
        return "redirect:/matches";
    }

    @PostMapping("/{id}/cancel")
    public String cancelMatch(@PathVariable String id, RedirectAttributes redirectAttributes) {
        mentorshipService.cancelMatch(id);
        redirectAttributes.addFlashAttribute("successMessage", "Match has been cancelled.");
        return "redirect:/matches";
    }
}
