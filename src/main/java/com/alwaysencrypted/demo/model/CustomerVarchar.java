package com.alwaysencrypted.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Customers_VARCHAR")
public class CustomerVarchar{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String ssn;  // This maps to the encrypted column

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }
}