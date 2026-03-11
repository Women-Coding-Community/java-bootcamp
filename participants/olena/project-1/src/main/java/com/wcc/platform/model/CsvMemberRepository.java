package com.wcc.platform.model;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class CsvMemberRepository implements MemberRepository {

    private final List<Member> members = new ArrayList<>();

    public void add(Member member) {
        members.add(member);
    }

    public List<Member> findAll() {
        return members;
    }

    public void saveToCsv(String filePath) throws IOException {

        File file = new File(filePath);
        boolean fileExists = file.exists();
        boolean fileEmpty = !fileExists || file.length() == 0;

       try(FileWriter writer = new FileWriter(file)) {

           if (fileEmpty) {
               writer.write(String.format("name,email,location,joinDate%n"));
           }

           for (Member member : members) {
               writer.write(String.format("%s,%s,%s,%s%n",
                       member.getName(),
                       member.getEmail(),
                       member.getLocation(),
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

        BufferedReader reader = new BufferedReader(new FileReader(filePath));

        String line;

        reader.readLine();

        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            String name = parts[0].trim();
            String email = parts[1].trim();
            String location = parts[2].trim();
            LocalDate joinDate = LocalDate.parse(parts[3].trim());

            Member member = new Member(
                    name,
                    email,
                    location,
                    List.of(),
                    joinDate
            );
            members.add(member);
        }
        reader.close();
    }
}

