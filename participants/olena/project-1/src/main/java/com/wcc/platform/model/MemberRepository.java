package com.wcc.platform.model;

import java.io.IOException;
import java.util.List;

public interface MemberRepository {

    void add(Member member);

    List<Member> findAll();

    void deleteByEmail(String email);

    void updateMember(String email, String newName, String newEmail, String newLocation, String newSkills);

    List<Member> findByLocation(String location);

    List<Member> findBySkill(String skill);

    List<Member> sortByName();

    List<Member> sortByJoinDate();

    void saveToCsv(String filePath) throws IOException;

    void loadFromCsv(String filePath) throws IOException;
}