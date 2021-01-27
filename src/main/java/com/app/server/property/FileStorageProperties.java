package com.app.server.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "storage")
public class FileStorageProperties {
    private String uploadDir;

    private String licenseTemplateDir;

    private String licenseTemplateName;

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

    public String getLicenseTemplateName() {
        return licenseTemplateName;
    }

    public void setLicenseTemplateName(String licenseTemplateName) {
        this.licenseTemplateName = licenseTemplateName;
    }
}
