package com.red.os_api.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "categories")
public class Categories {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    int category_id;

    @Column(name = "name",unique = true,nullable = false,length = 50)
    String name;

    @Column(name = "description")
    String description;

    public Categories(int category_id, String name, String description) {
        this.category_id = category_id;
        this.name = name;
        this.description = description;
    }

    public Categories(){

    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
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
        return "Categories{" +
                "category_id=" + category_id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
