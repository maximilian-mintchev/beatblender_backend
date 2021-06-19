package com.app.server.messages.response;

import com.app.server.model.user.Artist;

public class UserDataMessage {

    Artist artist;

    public UserDataMessage() {
    }

    public UserDataMessage(Artist artist) {
        this.artist = artist;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }
}
