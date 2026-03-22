package com.wcc.platform.service;

import com.wcc.platform.model.Member;
import com.wcc.platform.model.MemberRepository;

import java.util.List;

public class MemberSortService {

    private final MemberRepository repository;

    public MemberSortService(MemberRepository repository) {
        this.repository = repository;
    }

    public List<Member> sortByName() {
        return repository.sortByName();
    }

    public List<Member> sortByJoinDate() {
        return repository.sortByJoinDate();
    }
}
