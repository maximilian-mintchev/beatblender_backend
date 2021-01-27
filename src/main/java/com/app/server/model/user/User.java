package com.app.server.model.user;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "users")
//@Inheritance(strategy = InheritanceType.JOINED)
public class User {

    //    @GenericGenerator(
//            name="UUID",
//            strategy = "org.hibernate.id.UUIDGenerator"
//    )
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    @org.hibernate.annotations.Type(type="uuid-char")
//    @Column(name="user_id", updatable = true, nullable = false)
//    @Id
//    @GeneratedValue
    @Id
//    @GeneratedValue(generator = "uuid")
//    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "uuid", unique = true)
    private String uuid;
//    private UUID uuid;

    @Column(name = "email", updatable = true, nullable = false)
    private String email;

    @Column(name="creation_date", updatable = false, nullable = false)
    private LocalDateTime creationDate;

    public User() {
    }

    public User(String id, String email) {
        this.uuid = id;
        this.email = email;
        creationDate = LocalDateTime.now();
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
}
