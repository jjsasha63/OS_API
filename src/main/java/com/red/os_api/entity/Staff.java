package com.red.os_api.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "staff")
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    int staff_id;

    @Column(name = "name",nullable = false,length = 50)
    String name;

    @Column(name = "email",nullable = false,unique = true,length = 50)
    String email;

    @Column(name = "job_title",nullable = false,length = 50)
    String job_title;

    @ManyToOne
    @JoinColumn(name = "role_id")
    Role role;

    public Staff(int staff_id, String name, String email, String job_title) {
        this.staff_id = staff_id;
        this.name = name;
        this.email = email;
        this.job_title = job_title;
    }

    public Staff(){

    }

    public int getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(int staff_id) {
        this.staff_id = staff_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJob_title() {
        return job_title;
    }

    public void setJob_title(String job_title) {
        this.job_title = job_title;
    }

    public Role getRoles() {
        return role;
    }

    public void setRoles(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Staff{" +
                "staff_id=" + staff_id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", job_title='" + job_title + '\'' +
                ", roles=" + role +
                '}';
    }
}
