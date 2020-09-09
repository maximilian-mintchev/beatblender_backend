package com.app.server.messages.response;

public class BasicUserResponse {

    private int licensePoints;

    public BasicUserResponse() {
    }

    public BasicUserResponse(int licensePoints) {
        this.licensePoints = licensePoints;
    }

    public int getLicensePoints() {
        return licensePoints;
    }

    public void setLicensePoints(int licensePoints) {
        this.licensePoints = licensePoints;
    }
}
