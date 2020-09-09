package com.app.server.messages.response;

public class AudioFileResponse {

    private FileResponse[] audioFileResponse;

    public AudioFileResponse() {
    }

    public AudioFileResponse(FileResponse[] audioFileResponse) {
        this.audioFileResponse = audioFileResponse;
    }

    public FileResponse[] getAudioFileResponse() {
        return audioFileResponse;
    }

    public void setAudioFileResponse(FileResponse[] audioFileResponse) {
        this.audioFileResponse = audioFileResponse;
    }
}
