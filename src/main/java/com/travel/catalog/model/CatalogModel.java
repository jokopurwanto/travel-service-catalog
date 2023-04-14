package com.travel.catalog.model;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "tb_packages")
public class CatalogModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id")
    private Integer id;

    @Column(name = "destination")
    private String name;

    @Column(name = "price")
    private String price;

    @Column(name = "availability")
    private Integer availability;

    @Column(name = "date")
    private Timestamp date;

    public CatalogModel() {
    }

    public CatalogModel(Integer id, String name, String price, Integer availability, Timestamp date) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.availability = availability;
        this.date = date;
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

    public Integer getAvailability() {
        return availability;
    }

    public void setAvailability(Integer availability) {
        this.availability = availability;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }
}

