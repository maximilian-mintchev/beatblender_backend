package com.app.server.property;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="realm")
public class RealmProperties {

    String artistRole;
    String adminUsername;
    String password;

    public String getArtistRole() {
        return artistRole;
    }

    public void setArtistRole(String artistRole) {
        this.artistRole = artistRole;
    }

    public String getAdminUsername() {
        return adminUsername;
    }

    public void setAdminUsername(String adminUsername) {
        this.adminUsername = adminUsername;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
