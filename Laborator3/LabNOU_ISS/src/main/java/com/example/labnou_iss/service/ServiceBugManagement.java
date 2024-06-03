package com.example.labnou_iss.service;

import com.example.labnou_iss.domain.Angajat;
import com.example.labnou_iss.domain.Bug;
import com.example.labnou_iss.repository.AngajatRepository;
import com.example.labnou_iss.repository.BugRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ServiceBugManagement {

    private AngajatRepository angajatRepository;
    private BugRepository bugRepository;
    private Map<String, IObserver> loggedInTetsers;
    private Map<String, IObserver> loggedInProgramators;

    public ServiceBugManagement(AngajatRepository angajatRepository, BugRepository bugRepository) {
        this.angajatRepository = angajatRepository;
        this.bugRepository = bugRepository;
        this.loggedInTetsers = new ConcurrentHashMap<>();
        this.loggedInProgramators = new ConcurrentHashMap<>();
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

    public void addBug(Bug bug) {
        bugRepository.save(bug);
        for (IObserver observer : loggedInTetsers.values()) {
            observer.init_model();
        }
        for (IObserver observer : loggedInProgramators.values()) {
            observer.init_model();
        }
    }

    public Iterable<Bug> findAllBugs() {
        return bugRepository.findAll();
    }

    public Angajat handleLogin(String username, String password) {
        return angajatRepository.findAngajatByUsernamePassword(username, password);
    }

    public void loginTester(Angajat angajat, IObserver controller) {
        loggedInTetsers.put(angajat.getUsername(), controller);
    }

    public void loginProgramator(Angajat angajat, IObserver controller) {
        loggedInTetsers.put(angajat.getUsername(), controller);
    }


    public void deleteBug(Bug bug){
        this.bugRepository.delete(bugRepository.getBugId(bug));
        for (IObserver observer : loggedInProgramators.values()) {
            observer.init_model();
        }
        for (IObserver observer : loggedInTetsers.values()) {
            observer.init_model();
        }
    }

    public Bug findBugById(UUID bugUUID) {
        return bugRepository.findBugById(bugUUID);
    }

    public void updateBug(Bug bug) {
        bugRepository.updateBug(bug);
        for (IObserver observer : loggedInProgramators.values()) {
            observer.init_model();
        }
        for (IObserver observer : loggedInTetsers.values()) {
            observer.init_model();
        }
    }
}
