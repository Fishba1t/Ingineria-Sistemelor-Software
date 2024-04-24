package com.example.labnou_iss.service;

import com.example.labnou_iss.domain.Angajat;
import com.example.labnou_iss.domain.Bug;
import com.example.labnou_iss.repository.AngajatRepository;
import com.example.labnou_iss.repository.BugRepository;

import java.util.UUID;

public class ServiceBugManagement {

    private AngajatRepository angajatRepository;
    private BugRepository bugRepository;

    public ServiceBugManagement(AngajatRepository angajatRepository,BugRepository bugRepository){
        this.angajatRepository=angajatRepository;
        this.bugRepository=bugRepository;
    }

    public void addAngajat(Angajat angajat) {
        angajatRepository.save(angajat);
    }

    public Iterable<Angajat> findAllAngajati() {
        return angajatRepository.findAll();
    }

    public Angajat findAngajatByUsername(String username) {
        return angajatRepository.findOneByUsername(username);
    }

    public void addBug(UUID id, String name, String description, String status) {
        Bug bug = new Bug(id, name, description, status);
        bugRepository.save(bug);
    }

    public Iterable<Bug> findAllBugs() {
        return bugRepository.findAll();
    }

    public Angajat findAngajatByUsernamePassword(String username, String password) {
        return angajatRepository.findAngajatByUsernamePassword(username, password);
    }

}
