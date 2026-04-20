package com.wcc.bootcamp.java.mentorship;

import com.wcc.bootcamp.java.mentorship.model.Match;
import com.wcc.bootcamp.java.mentorship.model.Mentee;
import com.wcc.bootcamp.java.mentorship.model.Mentor;
import com.wcc.bootcamp.java.mentorship.service.MentorshipMatcher;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * Main application for the Mentorship Matcher system.
 * Provides a console-based interface for managing mentorship relationships.
 */
public class MentorshipMatcherApp {
    private final MentorshipMatcher matcher;
    private final Scanner scanner;

    public MentorshipMatcherApp() {
        this.matcher = new MentorshipMatcher();
        this.scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        MentorshipMatcherApp app = new MentorshipMatcherApp();
        
        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║         WELCOME TO MENTORSHIP MATCHER                        ║");
        System.out.println("║         Connecting Mentors and Mentees                       ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        
        // Run demo with sample data
        if (args.length > 0 && args[0].equals("--demo")) {
            app.runDemo();
        } else {
            app.runInteractiveMenu();
        }
    }

    /**
     * Runs the interactive menu system.
     */
    public void runInteractiveMenu() {
        boolean running = true;

        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    registerMentorInteractive();
                    break;
                case "2":
                    registerMenteeInteractive();
                    break;
                case "3":
                    matcher.displayAllMentors();
                    break;
                case "4":
                    matcher.displayAllMentees();
                    break;
                case "5":
                    matcher.displayAllPotentialMatches();
                    break;
                case "6":
                    createMatchInteractive();
                    break;
                case "7":
                    matcher.displayActiveMatches();
                    break;
                case "8":
                    unmatchInteractive();
                    break;
                case "9":
                    rematchInteractive();
                    break;
                case "10":
                    matcher.saveMatchesToFile();
                    break;
                case "11":
                    exportReportInteractive();
                    break;
                case "12":
                    loadSampleData();
                    System.out.println("✓ Sample data loaded!");
                    break;
                case "0":
                    running = false;
                    System.out.println("\nThank you for using Mentorship Matcher. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void printMenu() {
        System.out.println("\n┌────────────────────────────────────────┐");
        System.out.println("│           MAIN MENU                    │");
        System.out.println("├────────────────────────────────────────┤");
        System.out.println("│  1. Register Mentor                    │");
        System.out.println("│  2. Register Mentee                    │");
        System.out.println("│  3. View All Mentors                   │");
        System.out.println("│  4. View All Mentees                   │");
        System.out.println("│  5. Find Potential Matches             │");
        System.out.println("│  6. Create Match                       │");
        System.out.println("│  7. View Active Matches                │");
        System.out.println("│  8. Unmatch                            │");
        System.out.println("│  9. Rematch                            │");
        System.out.println("│ 10. Save Matches to File               │");
        System.out.println("│ 11. Export Detailed Report             │");
        System.out.println("│ 12. Load Sample Data                   │");
        System.out.println("│  0. Exit                               │");
        System.out.println("└────────────────────────────────────────┘");
        System.out.print("Enter your choice: ");
    }

    private void registerMentorInteractive() {
        System.out.println("\n── REGISTER MENTOR ──");
        
        System.out.print("Enter mentor name: ");
        String name = scanner.nextLine().trim();
        
        System.out.print("Enter email: ");
        String email = scanner.nextLine().trim();
        
        System.out.print("Enter expertise areas (comma-separated): ");
        String expertiseInput = scanner.nextLine().trim();
        List<String> expertise = Arrays.asList(expertiseInput.split(","));
        
        System.out.print("Enter max number of mentees (default 3): ");
        String maxInput = scanner.nextLine().trim();
        int maxMentees = maxInput.isEmpty() ? 3 : Integer.parseInt(maxInput);
        
        matcher.registerMentor(name, email, expertise, maxMentees);
    }

    private void registerMenteeInteractive() {
        System.out.println("\n── REGISTER MENTEE ──");
        
        System.out.print("Enter mentee name: ");
        String name = scanner.nextLine().trim();
        
        System.out.print("Enter email: ");
        String email = scanner.nextLine().trim();
        
        System.out.print("Enter learning goals (comma-separated): ");
        String goalsInput = scanner.nextLine().trim();
        List<String> goals = Arrays.asList(goalsInput.split(","));
        
        System.out.print("Enter experience level (beginner/intermediate/advanced): ");
        String level = scanner.nextLine().trim();
        if (level.isEmpty()) level = "beginner";
        
        matcher.registerMentee(name, email, goals, level);
    }

    private void createMatchInteractive() {
        System.out.println("\n── CREATE MATCH ──");
        
        matcher.displayAllMentors();
        System.out.print("Enter mentor name: ");
        String mentorName = scanner.nextLine().trim();
        
        matcher.displayAllMentees();
        System.out.print("Enter mentee name: ");
        String menteeName = scanner.nextLine().trim();
        
        Optional<Mentor> mentor = matcher.findMentorByName(mentorName);
        Optional<Mentee> mentee = matcher.findMenteeByName(menteeName);
        
        if (mentor.isPresent() && mentee.isPresent()) {
            matcher.createMatch(mentor.get(), mentee.get());
        } else {
            System.out.println("✗ Mentor or mentee not found.");
        }
    }

    private void unmatchInteractive() {
        System.out.println("\n── UNMATCH ──");
        
        matcher.displayActiveMatches();
        
        System.out.print("Enter mentee name to unmatch: ");
        String menteeName = scanner.nextLine().trim();
        
        Optional<Match> match = matcher.getMatches().stream()
                .filter(m -> m.getMentee().getName().equalsIgnoreCase(menteeName) 
                        && m.getStatus() == Match.MatchStatus.ACTIVE)
                .findFirst();
        
        if (match.isPresent()) {
            matcher.unmatch(match.get());
        } else {
            System.out.println("✗ Active match not found for mentee: " + menteeName);
        }
    }

    private void rematchInteractive() {
        System.out.println("\n── REMATCH ──");
        
        System.out.print("Enter mentee name: ");
        String menteeName = scanner.nextLine().trim();
        
        Optional<Mentee> mentee = matcher.findMenteeByName(menteeName);
        if (!mentee.isPresent()) {
            System.out.println("✗ Mentee not found.");
            return;
        }
        
        // Show potential matches for this mentee
        List<Match> potentialMatches = matcher.findMatchesForMentee(mentee.get());
        if (potentialMatches.isEmpty()) {
            System.out.println("✗ No potential mentors available.");
            return;
        }
        
        System.out.println("Potential mentors:");
        for (int i = 0; i < potentialMatches.size(); i++) {
            Match m = potentialMatches.get(i);
            System.out.printf("  %d. %s (Score: %.0f%%)%n", 
                    i + 1, m.getMentor().getName(), m.getMatchScore() * 100);
        }
        
        System.out.print("Enter new mentor name: ");
        String mentorName = scanner.nextLine().trim();
        
        Optional<Mentor> newMentor = matcher.findMentorByName(mentorName);
        if (newMentor.isPresent()) {
            matcher.rematch(mentee.get(), newMentor.get());
        } else {
            System.out.println("✗ Mentor not found.");
        }
    }

    private void exportReportInteractive() {
        System.out.print("Enter filename (default: mentorship_report.txt): ");
        String filename = scanner.nextLine().trim();
        if (filename.isEmpty()) {
            filename = "mentorship_report.txt";
        }
        matcher.exportDetailedReport(filename);
    }

    /**
     * Loads sample data for demonstration.
     */
    public void loadSampleData() {
        // Register mentors
        matcher.registerMentor("Alice Johnson", "alice@example.com",
                Arrays.asList("Java", "Spring Boot", "Microservices", "SQL"));
        
        matcher.registerMentor("Bob Smith", "bob@example.com",
                Arrays.asList("Python", "Machine Learning", "Data Science", "TensorFlow"));
        
        matcher.registerMentor("Carol Williams", "carol@example.com",
                Arrays.asList("JavaScript", "React", "Node.js", "TypeScript", "CSS"));
        
        matcher.registerMentor("David Brown", "david@example.com",
                Arrays.asList("Java", "Kotlin", "Android", "Mobile Development"));
        
        matcher.registerMentor("Eva Martinez", "eva@example.com",
                Arrays.asList("DevOps", "Docker", "Kubernetes", "AWS", "CI/CD"));

        // Register mentees
        matcher.registerMentee("Frank Lee", "frank@example.com",
                Arrays.asList("Java", "Spring"), "beginner");
        
        matcher.registerMentee("Grace Chen", "grace@example.com",
                Arrays.asList("Machine Learning", "Python", "Data Analysis"), "intermediate");
        
        matcher.registerMentee("Henry Wilson", "henry@example.com",
                Arrays.asList("React", "JavaScript", "Frontend Development"), "beginner");
        
        matcher.registerMentee("Ivy Taylor", "ivy@example.com",
                Arrays.asList("Android", "Mobile Apps", "Kotlin"), "intermediate");
        
        matcher.registerMentee("Jack Anderson", "jack@example.com",
                Arrays.asList("Docker", "Cloud Computing", "AWS"), "advanced");
        
        matcher.registerMentee("Karen Davis", "karen@example.com",
                Arrays.asList("SQL", "Database Design", "Java"), "beginner");
    }

    /**
     * Runs a demonstration with sample data and actions.
     */
    public void runDemo() {
        System.out.println("\n🚀 Running Demo Mode...\n");
        
        // Load sample data
        loadSampleData();
        
        // Display all mentors and mentees
        matcher.displayAllMentors();
        matcher.displayAllMentees();
        
        // Show potential matches
        matcher.displayAllPotentialMatches();
        
        // Create some matches
        System.out.println("\n🔗 Creating matches...\n");
        
        Optional<Mentor> alice = matcher.findMentorByName("Alice Johnson");
        Optional<Mentee> frank = matcher.findMenteeByName("Frank Lee");
        Optional<Mentee> karen = matcher.findMenteeByName("Karen Davis");
        
        if (alice.isPresent() && frank.isPresent()) {
            matcher.createMatch(alice.get(), frank.get());
        }
        
        Optional<Mentor> bob = matcher.findMentorByName("Bob Smith");
        Optional<Mentee> grace = matcher.findMenteeByName("Grace Chen");
        
        if (bob.isPresent() && grace.isPresent()) {
            matcher.createMatch(bob.get(), grace.get());
        }
        
        Optional<Mentor> carol = matcher.findMentorByName("Carol Williams");
        Optional<Mentee> henry = matcher.findMenteeByName("Henry Wilson");
        
        if (carol.isPresent() && henry.isPresent()) {
            matcher.createMatch(carol.get(), henry.get());
        }
        
        // Display active matches
        matcher.displayActiveMatches();
        
        // Demonstrate rematch
        System.out.println("\n🔄 Demonstrating rematch functionality...\n");
        
        if (karen.isPresent() && alice.isPresent()) {
            // First match Karen with Alice
            Match karenMatch = matcher.createMatch(alice.get(), karen.get());
            
            // Show the match
            matcher.displayActiveMatches();
            
            // Now rematch Karen with David (who also knows Java)
            Optional<Mentor> david = matcher.findMentorByName("David Brown");
            if (david.isPresent()) {
                System.out.println("\n📝 Rematching Karen with David...");
                matcher.rematch(karen.get(), david.get());
            }
        }
        
        // Display final state
        matcher.displayActiveMatches();
        
        // Show remaining potential matches
        matcher.displayAllPotentialMatches();
        
        // Save to file
        matcher.saveMatchesToFile("demo_matches.txt");
        matcher.exportDetailedReport("demo_report.txt");
        
        System.out.println("\n✅ Demo completed!");
        System.out.println("Check 'demo_matches.txt' and 'demo_report.txt' for exported data.");
    }
}
