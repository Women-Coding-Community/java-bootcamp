package com.wcc.platform.model;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class CsvMemberRepository implements MemberRepository {

    private final List<Member> members = new ArrayList<>();

    public void add(Member member) {
        boolean exists = members.stream().anyMatch(m->m.getEmail().equalsIgnoreCase(member.getEmail()));

        if(exists) {
            System.out.println("Member already exists.");
            return;
        }
        members.add(member);
    }

    public List<Member> findAll() {

        return new ArrayList<>(members);
    }

    public void deleteByEmail(String email) {

        members.removeIf(m -> m.getEmail().equalsIgnoreCase(email));
    }

    public void updateMember(String email, String newName, String newEmail, String newLocation, String newSkills) {

        for (int i = 0; i < members.size(); i++) {

            Member m = members.get(i);

            if (m.getEmail().equalsIgnoreCase(email)) {

                Member updatedMember = new Member(
                        newName,
                        newEmail,
                        newLocation,
                        List.of(newSkills.split(",")),
                        m.getJoinDate()
                );

                members.set(i, updatedMember);

                System.out.println("Member updated.");

                return;
            }
        }

        System.out.println("Member not found.");
    }

    public List<Member> findByLocation(String location) {

        return members.stream()
                .filter(m -> m.getLocation().equalsIgnoreCase(location))
                .toList();
    }

    public List<Member> findBySkill(String skill) {

        return members.stream()
                .filter(m -> m.getSkills()
                        .stream()
                        .anyMatch(s -> s.equalsIgnoreCase(skill)))
                .toList();
    }

    public List<Member> sortByName() {

        return members.stream()
                .sorted((a, b) -> a.getName().compareToIgnoreCase(b.getName()))
                .toList();
    }

    public List<Member> sortByJoinDate() {

        return members.stream()
                .sorted((a, b) -> a.getJoinDate().compareTo(b.getJoinDate()))
                .toList();
    }

    public void saveToCsv(String filePath) throws IOException {

        try (FileWriter writer = new FileWriter(filePath)) {

            writer.write("name,email,location,skills,joinDate\n");

            for (Member member : members) {

                String skills = String.join("|", member.getSkills());

                writer.write(String.format("%s,%s,%s,%s,%s%n",
                        member.getName(),
                        member.getEmail(),
                        member.getLocation(),
                        skills,
                        member.getJoinDate()
                ));
            }
        }
    }

    public void loadFromCsv(String filePath) throws IOException {
        File file = new File(filePath);
        if(!file.exists()){
            return;
        }

        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String name = parts[0].trim();
                String email = parts[1].trim();
                String location = parts[2].trim();
                String skills = parts[3].trim();
                List<String> skillList = List.of(skills.split("\\|"));
                LocalDate joinDate = LocalDate.parse(parts[4].trim());

                Member member = new Member(
                        name,
                        email,
                        location,
                        skillList,
                        joinDate
                );
                members.add(member);
            }
        }
    }
}
