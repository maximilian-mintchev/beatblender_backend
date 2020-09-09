package com.app.server.services;

import com.app.server.exceptions.FileStorageException;
import com.app.server.model.BasicUser;
import com.app.server.model.Sample;
import com.app.server.property.FileStorageProperties;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.tomcat.jni.Error;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class UtilityService {

    private final Path fileStorageLocation;

    public UtilityService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
    }


    public String handleOptionalFormData(String data) {
        System.out.println(data);
        if(!data.equals("null")) {
            System.out.println("data");
            return data;
        } else {
            System.out.println("empty");
            return null;
        }
    }

    public String writePDF(BasicUser uploader, BasicUser downloader, Sample sample) {
        String mypath ="/home/mintch/AppDokumente/Templates";
        Path licensePathLocation;
        Path basicLicensePath;
        Path basicLicensePathPDF;

        try {

//            PDDocument pDDocument = PDDocument.load(new File("/home/mintch/AppDokumente/Templates/template1.pdf"));
            PDDocument pDDocument = PDDocument.load(new File(String.valueOf(Paths.get(String.valueOf(this.fileStorageLocation), "AppDokumente","Templates", "template1.pdf"))));

            PDAcroForm pDAcroForm = pDDocument.getDocumentCatalog().getAcroForm();
            PDField field = pDAcroForm.getField("txt1");
            field.setValue(sample.getArtistName());
            PDField field1 = pDAcroForm.getField("txt2");
            field1.setValue(downloader.getBasicUserName());
            PDField field2 = pDAcroForm.getField("txt3");
            field2.setValue(sample.getSampleTitle());

            licensePathLocation = Paths.get(String.valueOf(this.fileStorageLocation), downloader.getBasicUserName(), "basicLicenses");

//            Path basicLicensePath = Paths.get(String.valueOf(this.fileStorageLocation), downloader.getBasicUserName(), "basicLicenses", String.valueOf(sample.getId()));

            basicLicensePath = licensePathLocation.resolve(String.valueOf(sample.getId()));
            if(!Files.exists(basicLicensePath) && !Files.exists((licensePathLocation))) {
                Files.createDirectory(licensePathLocation);
            } else {
                System.out.println("basicLicensePath Directory already exists");
            }

            basicLicensePathPDF = Paths.get(String.valueOf(licensePathLocation), String.valueOf(sample.getId()) + ".pdf");
            pDDocument.save(new File(String.valueOf(basicLicensePathPDF)));


//            pDDocument.save(new File("/home/mintch/AppDokumente/FinalContracts/mycontract3.pdf"));

            pDDocument.close();
            return String.valueOf(basicLicensePathPDF);

        } catch (IOException e) {
            e.printStackTrace();
            throw new FileStorageException("Could not create Basic License PDF", e);
        }





    }


}

