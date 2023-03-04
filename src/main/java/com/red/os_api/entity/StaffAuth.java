package com.red.os_api.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "staff_auth")
public class StaffAuth {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    int staff_auth_id;

    @OneToOne
    @JoinColumn(name = "staff_id")
    Staff staff;

    @Column(name = "username",nullable = false,unique = true,length = 50)
    String username;

    @Column(name = "password_hash",nullable = false,length = 100)
    String password_hash;

    public StaffAuth(int staff_auth_id, String username, String password_hash) {
        this.staff_auth_id = staff_auth_id;
        this.username = username;
        this.password_hash = password_hash;
    }

    public StaffAuth(){

    }

    public int getStaff_auth_id() {
        return staff_auth_id;
    }

    public void setStaff_auth_id(int staff_auth_id) {
        this.staff_auth_id = staff_auth_id;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword_hash() {
        return password_hash;
    }

    public void setPassword_hash(String password_hash) {
        this.password_hash = password_hash;
    }

    @Override
    public String toString() {
        return "StaffAuth{" +
                "staff_auth_id=" + staff_auth_id +
                ", staff=" + staff +
                ", username='" + username + '\'' +
                ", password_hash='" + password_hash + '\'' +
                '}';
    }
}
