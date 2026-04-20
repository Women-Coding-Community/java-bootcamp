package com.wcc.bootcamp.java.mentorship.controller;

import com.wcc.bootcamp.java.mentorship.service.MentorshipService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller for the home page and dashboard.
 */
@Controller
public class HomeController {

    private final MentorshipService mentorshipService;

    public HomeController(MentorshipService mentorshipService) {
        this.mentorshipService = mentorshipService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("stats", mentorshipService.getStatistics());
        model.addAttribute("recentMatches", mentorshipService.getActiveMatches());
        return "home";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }
}
