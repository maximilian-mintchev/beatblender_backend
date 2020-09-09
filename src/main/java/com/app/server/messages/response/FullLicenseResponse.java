package com.app.server.messages.response;

import com.app.server.enums.ResponseStatus;

public class FullLicenseResponse {


    ResponseStatus responseStatus;
    int licensePointsDownloader;
    String message ="Congratulations, you now have a Full License!";

    public FullLicenseResponse(ResponseStatus responseStatus, int licensePointsDownloader) {
        this.responseStatus = responseStatus;
        this.licensePointsDownloader = licensePointsDownloader;

    }

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public int getLicensePointsDownloader() {
        return licensePointsDownloader;
    }

    public void setLicensePointsDownloader(int licensePointsDownloader) {
        this.licensePointsDownloader = licensePointsDownloader;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
