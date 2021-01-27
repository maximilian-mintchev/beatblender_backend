package com.app.server.services.pdf;

import com.app.server.exceptions.FileStorageException;
import com.app.server.model.audio.Sample;
import com.app.server.model.user.Artist;
import com.app.server.model.user.User;
import com.app.server.property.FileStorageProperties;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class PDFService {

    private final Path fileStorageLocation;
    private final Path licenseTemplateDir;

    public PDFService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
        this.licenseTemplateDir = Paths.get(fileStorageProperties.getUploadDir(), fileStorageProperties.getLicenseTemplateDir(), fileStorageProperties.getLicenseTemplateName());
    }

    public String writePDF(Artist uploaderArtist, User downloader, Sample sample) {
//        String mypath ="/home/mintch/AppDokumente/Templates";
        Path licensePathLocation;
        Path basicLicensePath;
        Path basicLicensePathPDF;

        try {
//            todo vertragsfelder anpassen; echter Name whs erforderlich.
//            PDDocument pDDocument = PDDocument.load(new File("/home/mintch/AppDokumente/Templates/template1.pdf"));
//            PDDocument pDDocument = PDDocument.load(new File(String.valueOf(Paths.get(String.valueOf(this.fileStorageLocation), "AppDokumente","Templates", "template1.pdf"))));
            PDDocument pDDocument = PDDocument.load(new File(String.valueOf(this.licenseTemplateDir)));
            PDAcroForm pDAcroForm = pDDocument.getDocumentCatalog().getAcroForm();
            PDField field = pDAcroForm.getField("txt1");
            field.setValue(sample.getAudioUnit().getArtistAlias().getArtistName());
            PDField field1 = pDAcroForm.getField("txt2");
            field1.setValue(downloader.getEmail());
            PDField field2 = pDAcroForm.getField("txt3");
            field2.setValue(sample.getAudioUnit().getTitle());

//            licensePathLocation = Paths.get(String.valueOf(this.fileStorageLocation), downloader.getBasicUserName(), "basicLicenses");
            licensePathLocation = Paths.get(String.valueOf(this.fileStorageLocation), downloader.getUuid(), "basicLicenses");

//            Path basicLicensePath = Paths.get(String.valueOf(this.fileStorageLocation), downloader.getBasicUserName(), "basicLicenses", String.valueOf(sample.getId()));

            basicLicensePath = licensePathLocation.resolve(sample.getAudioUnit().getAudioUnitID().toString());
            if (!Files.exists(basicLicensePath) && !Files.exists((licensePathLocation))) {
                Files.createDirectories(licensePathLocation);
            } else {
                System.out.println("basicLicensePath Directory already exists");
            }

            basicLicensePathPDF = Paths.get(String.valueOf(licensePathLocation), sample.getAudioUnit().getAudioUnitID().toString() + ".pdf");
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
