package com.app.server.messages.response;

import com.app.server.enums.ResponseStatus;
import com.app.server.model.audio.MixedIn;
import com.app.server.model.license.BasicLicense;
import com.app.server.model.license.FullLicense;

import java.util.List;

public class FullLicenseResponse {

    private FullLicense fullLicense;
    private List<BasicLicense> basicLicenseList;

    public FullLicenseResponse() {
    }

    public FullLicenseResponse(FullLicense fullLicense, List<BasicLicense> basicLicenseList) {
        this.fullLicense = fullLicense;
        this.basicLicenseList = basicLicenseList;
    }

    public FullLicense getFullLicense() {
        return fullLicense;
    }

    public void setFullLicense(FullLicense fullLicense) {
        this.fullLicense = fullLicense;
    }

    public List<BasicLicense> getBasicLicenseList() {
        return basicLicenseList;
    }

    public void setBasicLicenseList(List<BasicLicense> basicLicenseList) {
        this.basicLicenseList = basicLicenseList;
    }

    //    ResponseStatus responseStatus;
//    int licensePointsDownloader;
//    String message ="Congratulations, you now have a Full License!";
//
//    public FullLicenseResponse(ResponseStatus responseStatus, int licensePointsDownloader) {
//        this.responseStatus = responseStatus;
//        this.licensePointsDownloader = licensePointsDownloader;
//
//    }
//
//    public ResponseStatus getResponseStatus() {
//        return responseStatus;
//    }
//
//    public void setResponseStatus(ResponseStatus responseStatus) {
//        this.responseStatus = responseStatus;
//    }
//
//    public int getLicensePointsDownloader() {
//        return licensePointsDownloader;
//    }
//
//    public void setLicensePointsDownloader(int licensePointsDownloader) {
//        this.licensePointsDownloader = licensePointsDownloader;
//    }
//
//    public String getMessage() {
//        return message;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }
}
