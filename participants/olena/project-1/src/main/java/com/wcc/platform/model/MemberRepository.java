package com.wcc.platform.member;

import java.util.ArrayList;
import java.util.List;
//repository is responsible for saving, retrieving data
public class MemberRepository {

    //create object
    private final List<Member> members = new ArrayList<>();

    public void add(Member member) {
        members.add(member);
    }

    public List<Member> findAll() {
        return members;
    }
}