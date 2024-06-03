package com.example.labnou_iss.domain;

import java.util.Objects;
import java.util.UUID;

public class Angajat extends Entity<UUID>{

    private String username;
    private String password;
    private String position;

    public Angajat(UUID id, String username, String password, String position){
        super.setId(id);
        this.username=username;
        this.password=password;
        this.position=position;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "Angajat{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", position='" + position + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Angajat angajat = (Angajat) o;
        return Objects.equals(username, angajat.username) && Objects.equals(password, angajat.password) && Objects.equals(position, angajat.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), username, password, position);
    }

}
