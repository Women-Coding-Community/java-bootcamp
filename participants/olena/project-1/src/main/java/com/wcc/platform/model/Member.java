package com.wcc.platform.model;

import java.time.LocalDate;
import java.util.List;

public class Member {
    private final String name;
    private final String email;
    private final String location;
    private final List<String> skills;
    private final LocalDate joinDate;


    public Member(
            String name,
            String email,
            String location,
            List<String> skills,
            LocalDate joinDate
            ) {
        this.name = name;
        this.email = email;
        this.location = location;
        this.skills = skills;
        this.joinDate = joinDate;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getLocation() {
        return location;
    }

    public List<String> getSkills() {
        return skills;
    }

    public LocalDate getJoinDate() {
        return joinDate;
    }

    @Override
    public String toString() {
        return String.format("%s  %s  %s", name, email, location);
    }
}




