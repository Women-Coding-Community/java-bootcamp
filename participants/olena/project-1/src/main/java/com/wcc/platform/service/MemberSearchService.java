package com.wcc.platform.service;

import com.wcc.platform.model.Member;
import com.wcc.platform.model.MemberRepository;

import java.util.List;

public class MemberSearchService {

    private final MemberRepository repository;

    public MemberSearchService(MemberRepository repository) {
        this.repository = repository;
    }

    public List<Member> findByLocation(String location) {
        return repository.findByLocation(location);
    }

    public List<Member> findBySkill(String skill) {
        return repository.findBySkill(skill);
    }
}
