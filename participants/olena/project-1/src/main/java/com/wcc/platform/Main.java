package com.wcc.platform;


import com.wcc.platform.cli.MemberCli;
import com.wcc.platform.model.MemberRepository;
import com.wcc.platform.model.CsvMemberRepository;

import java.io.IOException;

public class Main {



    public static void main(String[] args) throws IOException {
        MemberRepository repository = new CsvMemberRepository();
        repository.loadFromCsv("members.csv");
        MemberCli cli = new MemberCli(repository);
        cli.start();
    }
}

