package com.wcc.platform.service;

import com.wcc.platform.model.Member;
import com.wcc.platform.model.MemberRepository;

import java.io.IOException;
import java.util.List;

public class MemberDataOperations {

    private final MemberRepository repository;
    private static final String FILE_NAME = "members.csv";

    public MemberDataOperations(MemberRepository repository) {
        this.repository = repository;
    }

    public void addMember(Member member) throws IOException {
        repository.add(member);
        repository.saveToCsv(FILE_NAME);
    }

    public void updateMember(String email,
                             String newName,
                             String newEmail,
                             String newLocation,
                             String newSkills) throws IOException {

        repository.updateMember(email, newName, newEmail, newLocation, newSkills);
        repository.saveToCsv(FILE_NAME);
    }

    public void deleteMember(String email) throws IOException {
        repository.deleteByEmail(email);
        repository.saveToCsv(FILE_NAME);
    }

    public List<Member> findAll() {
        return repository.findAll();
    }

}
