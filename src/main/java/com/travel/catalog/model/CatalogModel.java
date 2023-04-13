package com.travel.catalog.model;

import jakarta.persistence.*;

@Entity
@Table(name = "packages")
public class CatalogModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private String price;

    public CatalogModel() {
    }

    public CatalogModel(Integer id, String name, String price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}

