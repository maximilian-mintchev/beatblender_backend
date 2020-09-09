package com.app.server.messages.response;

import com.app.server.enums.ResponseStatus;

public class BasicLicenseResponse {


    private ResponseStatus responseStatus;
    private Long sampleID;
    private Long downLoaderID;
    private Long uplaoderID;
    private int fullLicensePrice;
    private String basicLicensePath;




    public BasicLicenseResponse() {
    }

    public BasicLicenseResponse(ResponseStatus responseStatus, Long sampleID, Long downLoaderID, Long uplaoderID, int fullLicensePrice, String basicLicensePath) {
        this.responseStatus = responseStatus;
        this.fullLicensePrice = fullLicensePrice;
        this.basicLicensePath = basicLicensePath;
        this.sampleID = sampleID;
        this.downLoaderID = downLoaderID;
        this.uplaoderID = uplaoderID;
    }

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public int getFullLicensePrice() {
        return fullLicensePrice;
    }

    public void setFullLicensePrice(int fullLicensePrice) {
        this.fullLicensePrice = fullLicensePrice;
    }

    public String getBasicLicensePath() {
        return basicLicensePath;
    }

    public void setBasicLicensePath(String basicLicensePath) {
        this.basicLicensePath = basicLicensePath;
    }

    public Long getSampleID() {
        return sampleID;
    }

    public void setSampleID(Long sampleID) {
        this.sampleID = sampleID;
    }

    public Long getDownLoaderID() {
        return downLoaderID;
    }

    public void setDownLoaderID(Long downLoaderID) {
        this.downLoaderID = downLoaderID;
    }

    public Long getUplaoderID() {
        return uplaoderID;
    }

    public void setUplaoderID(Long uplaoderID) {
        this.uplaoderID = uplaoderID;
    }
}
