package com.example.labnou_iss.domain;

import java.util.Objects;
import java.util.UUID;

public class Bug extends Entity<UUID>{

    private String name;
    private String description;
    private String status;

    public Bug(UUID id, String name, String description, String status){
        super.setId(id);
        this.name=name;
        this.description=description;
        this.status=status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Bug{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Bug bug = (Bug) o;
        return Objects.equals(name, bug.name) && Objects.equals(description, bug.description) && Objects.equals(status, bug.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, description, status);
    }


}
