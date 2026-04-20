package com.wcc.bootcamp.java.mentorship.controller;

import com.wcc.bootcamp.java.mentorship.dto.MentorRegistrationForm;
import com.wcc.bootcamp.java.mentorship.model.Match;
import com.wcc.bootcamp.java.mentorship.model.Mentor;
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
 * Controller for mentor-related operations.
 */
@Controller
@RequestMapping("/mentors")
public class MentorController {

    private final MentorshipService mentorshipService;

    public MentorController(MentorshipService mentorshipService) {
        this.mentorshipService = mentorshipService;
    }

    @GetMapping
    public String listMentors(Model model) {
        model.addAttribute("mentors", mentorshipService.getAllMentors());
        return "mentors/list";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("mentorForm", new MentorRegistrationForm());
        return "mentors/register";
    }

    @PostMapping("/register")
    public String registerMentor(@Valid @ModelAttribute("mentorForm") MentorRegistrationForm form,
                                  BindingResult result,
                                  RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "mentors/register";
        }

        // Parse comma-separated skills into a list
        List<String> skills = Arrays.stream(form.getSkills().split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());

        Mentor mentor = mentorshipService.registerMentor(
                form.getName(),
                form.getEmail(),
                skills,
                form.getMaxMentees()
        );

        redirectAttributes.addFlashAttribute("successMessage",
                "Welcome, " + mentor.getName() + "! You have been registered as a mentor.");

        return "redirect:/mentors";
    }

    @GetMapping("/{id}")
    public String viewMentor(@PathVariable String id, Model model) {
        Optional<Mentor> mentor = mentorshipService.findMentorById(id);
        
        if (mentor.isEmpty()) {
            return "redirect:/mentors";
        }

        List<Match> potentialMatches = mentorshipService.findMatchesForMentor(id);

        model.addAttribute("mentor", mentor.get());
        model.addAttribute("potentialMatches", potentialMatches);
        
        return "mentors/view";
    }

    @PostMapping("/{id}/delete")
    public String deleteMentor(@PathVariable String id, RedirectAttributes redirectAttributes) {
        mentorshipService.deleteMentor(id);
        redirectAttributes.addFlashAttribute("successMessage", "Mentor has been removed.");
        return "redirect:/mentors";
    }
}
