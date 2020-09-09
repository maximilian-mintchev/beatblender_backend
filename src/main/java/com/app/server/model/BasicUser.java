package com.app.server.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


@Entity
@Table(name = "basicUser")
public class BasicUser {

    public String getBasicUserName() {
        return basicUserName;
    }

    public void setBasicUserName(String basicUserName) {
        this.basicUserName = basicUserName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /*@NotBlank
    @Size(min=3, max = 50)*/
    private String basicUserName;

    private int licensePoints;


    public BasicUser() {}

    public int getLicensePoints() {
        return licensePoints;
    }

    public BasicUser(String userName) {
        this.basicUserName = userName;
        this.licensePoints = 0;

    }


    public int increaseLicensePoints() {
        licensePoints += 1;
        return licensePoints;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLicensePoints(int licensePoints) {
        this.licensePoints = licensePoints;
    }
}
