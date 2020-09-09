package com.app.server.model;


import javax.persistence.*;

@Entity
@Table(name="full_license")
public class FullLicense {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "basic_license_id", referencedColumnName = "id")
    private BasicLicense basicLicense;






}
