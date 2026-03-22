package com.wcc.platform.cli;

import com.wcc.platform.model.Member;

import java.util.List;

public class MemberPrinter {

    public void printHeader() {
        System.out.printf("%-15s %-25s %-15s %-20s%n",
                "Name", "Email", "Location", "Skills");
        System.out.println("---------------------------------------------------------------------------------");
    }

    public void printMembers(List<Member> members) {
        members.forEach(this::printMember);
    }

    public void printMember(Member member) {
        System.out.printf("%-15s %-25s %-15s %-20s%n",
                member.getName(),
                member.getEmail(),
                member.getLocation(),
                String.join("|", member.getSkills())
        );
    }
}
