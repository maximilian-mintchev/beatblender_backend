package com.app.server.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "storage")
public class FileStorageProperties {
    private String uploadDir;

    private String licenseTemplateDir;

    private String basicLicenseTemplateName;

    private String fullLicenseTemplateName;

    public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }

    public String getLicenseTemplateDir() {
        return licenseTemplateDir;
    }

    public void setLicenseTemplateDir(String licenseTemplateDir) {
        this.licenseTemplateDir = licenseTemplateDir;
    }

    public String getBasicLicenseTemplateName() {
        return basicLicenseTemplateName;
    }

    public void setBasicLicenseTemplateName(String basicLicenseTemplateName) {
        this.basicLicenseTemplateName = basicLicenseTemplateName;
    }

    public String getFullLicenseTemplateName() {
        return fullLicenseTemplateName;
    }

    public void setFullLicenseTemplateName(String fullLicenseTemplateName) {
        this.fullLicenseTemplateName = fullLicenseTemplateName;
    }
}
