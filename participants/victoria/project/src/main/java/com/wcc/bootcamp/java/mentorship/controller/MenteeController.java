package com.wcc.bootcamp.java.mentorship.controller;

import com.wcc.bootcamp.java.mentorship.dto.MenteeRegistrationForm;
import com.wcc.bootcamp.java.mentorship.model.Match;
import com.wcc.bootcamp.java.mentorship.model.Mentee;
import com.wcc.bootcamp.java.mentorship.service.MentorshipService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Controller for mentee-related operations.
 */
@Controller
@RequestMapping("/mentees")
public class MenteeController {

    private final MentorshipService mentorshipService;

    public MenteeController(MentorshipService mentorshipService) {
        this.mentorshipService = mentorshipService;
    }

    @GetMapping
    public String listMentees(Model model) {
        model.addAttribute("mentees", mentorshipService.getAllMentees());
        return "mentees/list";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("menteeForm", new MenteeRegistrationForm());
        return "mentees/register";
    }

    @PostMapping("/register")
    public String registerMentee(@Valid @ModelAttribute("menteeForm") MenteeRegistrationForm form,
                                  BindingResult result,
                                  RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "mentees/register";
        }

        // Parse comma-separated learning goals into a list
        List<String> goals = Arrays.stream(form.getLearningGoals().split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());

        Mentee mentee = mentorshipService.registerMentee(
                form.getName(),
                form.getEmail(),
                goals,
                form.getExperienceLevel()
        );

        redirectAttributes.addFlashAttribute("successMessage",
                "Welcome, " + mentee.getName() + "! You have been registered as a mentee.");

        return "redirect:/mentees";
    }

    @GetMapping("/{id}")
    public String viewMentee(@PathVariable String id, Model model) {
        Optional<Mentee> mentee = mentorshipService.findMenteeById(id);
        
        if (mentee.isEmpty()) {
            return "redirect:/mentees";
        }

        List<Match> potentialMatches = mentorshipService.findMatchesForMentee(id);

        model.addAttribute("mentee", mentee.get());
        model.addAttribute("potentialMatches", potentialMatches);
        
        return "mentees/view";
    }

    @PostMapping("/{id}/delete")
    public String deleteMentee(@PathVariable String id, RedirectAttributes redirectAttributes) {
        mentorshipService.deleteMentee(id);
        redirectAttributes.addFlashAttribute("successMessage", "Mentee has been removed.");
        return "redirect:/mentees";
    }
}
