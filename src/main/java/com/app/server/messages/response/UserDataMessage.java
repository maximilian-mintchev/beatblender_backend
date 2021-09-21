package com.app.server.messages.response;

import com.app.server.model.user.Artist;
import com.app.server.model.user.ArtistAlias;

public class UserDataMessage {

    ArtistAlias artistAlias;

    public UserDataMessage() {
    }

    public UserDataMessage(ArtistAlias artistAlias) {
        this.artistAlias = artistAlias;
    }

    public ArtistAlias getArtistAlias() {
        return artistAlias;
    }

    public void setArtistAlias(ArtistAlias artistAlias) {
        this.artistAlias = artistAlias;
    }
}
