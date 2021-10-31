package ru.job4j.dreamjob.model;

import java.util.Date;
import java.util.Objects;

public class Candidate {

    private int id;
    private Integer cityId;
    private String name;
    private String position;
    private Date created;

    public Candidate(int aId, String aName, String aPosition) {
        id = aId;
        name = aName;
        position = aPosition;
        created = new Date();
        cityId = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Candidate candidate = (Candidate) o;
        return id == candidate.id
               && Objects.equals(name, candidate.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return
                "Candidate{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", position='" + position + '\''
                + '}';
    }
}
