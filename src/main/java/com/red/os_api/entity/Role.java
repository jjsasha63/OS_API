package com.red.os_api.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    int role_id;

    @Column(name = "name",nullable = false,unique = true,length = 50)
    String name;

    @Column(name = "description")
    String description;

    public Role(int role_id, String name, String description) {
        this.role_id = role_id;
        this.name = name;
        this.description = description;
    }

    public Role(){

    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
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

    @Override
    public String toString() {
        return "Roles{" +
                "role_id=" + role_id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
