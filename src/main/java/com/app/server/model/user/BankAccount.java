package com.app.server.model.user;


import org.apache.tomcat.jni.Address;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name="bank_account")
public class BankAccount {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name="uuid", strategy = "uuid2")
    private String accountID;

    @Column(name="creation_date")
    private LocalDateTime creationDate;

    @Column(name="iban")
    private String iban;

    @Column(name="billing")
    private String accountHolder;

    @Column(name="bankName")
    private String bankName;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="artist_fk")
    private Artist artist;

    public BankAccount(String iban, String accountHolder, String bankName, Artist artist) {
        this.iban = iban;
        this.accountHolder = accountHolder;
        this.bankName = bankName;
        this.artist = artist;
        this.creationDate = LocalDateTime.now();
    }

    public BankAccount() {
    }

    public String getAccountID() {
        return accountID;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public void setAccountHolder(String accountHolder) {
        this.accountHolder = accountHolder;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }


}
