package com.app.server.messages.response;

public class FileResponse {


    private String id;
    private String artistName;
    private String sampleTitle;
    private String audioFile;
    private String sampleImage;
    private String audioFileDownLoadUri;
    private String imageDownLoadUri;
    private String genre;
    private String trackType;
    private String songKey;
    private String region;
    private String audioDescription;
    private Long sampleID;
    private int samplePrice;

//    private File audioMultiFile;
//    private File sampleMultiImage;



//    public File getAudioMultiFile() {
//        return audioMultiFile;
//    }
//
//    public void setAudioMultiFile(File audioMultiFile) {
//        this.audioMultiFile = audioMultiFile;
//    }

    public FileResponse() {
    }


    /*public FileResponse(String id, String artistName, String sampleTitle, String audioFile, String sampleImage, String audioFileDownLoadUri, String imageDownLoadUri) {
        this.id = id;
        this.artistName = artistName;
        this.sampleTitle = sampleTitle;
        this.audioFile = audioFile;
        this.sampleImage = sampleImage;
        this.audioFileDownLoadUri = audioFileDownLoadUri;
        this.imageDownLoadUri = imageDownLoadUri;
    }*/

    public FileResponse(String id, String artistName, String sampleTitle, String audioFile, String sampleImage,  String audioFileDownLoadUri, String imageDownLoadUri,String genre, String trackType, String songKey, String region, String audioDescription, Long sampleID, int samplePrice) {
        this.id = id;
        this.artistName = artistName;
        this.sampleTitle = sampleTitle;
        this.audioFile = audioFile;
        this.sampleImage = sampleImage;
        this.audioFileDownLoadUri = audioFileDownLoadUri;
        this.imageDownLoadUri = imageDownLoadUri;
        this.genre = genre;
        this.trackType = trackType;
        this.songKey = songKey;
        this.region = region;
        this.audioDescription = audioDescription;
        this.sampleID = sampleID;
        this.samplePrice = samplePrice;
    }

    //    public File getSampleMultiImage() {
//        return sampleMultiImage;
//    }
//
//    public void setSampleMultiImage(File sampleMultiImage) {
//        this.sampleMultiImage = sampleMultiImage;
//    }

//    public FileResponse(String id, String artistName, String sampleTitle, String audioFile, String sampleImage, File audioMultiFile, File sampleMultiImage) {
//        this.id = id;
//        this.artistName = artistName;
//        this.sampleTitle = sampleTitle;
//        this.audioFile = audioFile;
//        this.sampleImage = sampleImage;
//        this.audioMultiFile = audioMultiFile;
//        this.sampleMultiImage = sampleMultiImage;
//    }


//    public FileResponse(String id, String artistName, String sampleTitle, String audioFile, String sampleImage) {
//        this.id = id;
//        this.artistName = artistName;
//        this.sampleTitle = sampleTitle;
//        this.audioFile = audioFile;
//        this.sampleImage = sampleImage;
//    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }




    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getSampleTitle() {
        return sampleTitle;
    }

    public void setSampleTitle(String sampleTitle) {
        this.sampleTitle = sampleTitle;
    }

    public String getAudioFile() {
        return audioFile;
    }

    public void setAudioFile(String audioFile) {
        this.audioFile = audioFile;
    }

    public String getSampleImage() {
        return sampleImage;
    }

    public void setSampleImage(String sampleImage) {
        this.sampleImage = sampleImage;
    }

    public String getAudioFileDownLoadUri() {
        return audioFileDownLoadUri;
    }

    public void setAudioFileDownLoadUri(String audioFileDownLoadUri) {
        this.audioFileDownLoadUri = audioFileDownLoadUri;
    }

    public String getImageDownLoadUri() {
        return imageDownLoadUri;
    }

    public void setImageDownLoadUri(String imageDownLoadUri) {
        this.imageDownLoadUri = imageDownLoadUri;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getTrackType() {
        return trackType;
    }

    public void setTrackType(String trackType) {
        this.trackType = trackType;
    }

    public String getSongKey() {
        return songKey;
    }

    public void setSongKey(String songKey) {
        this.songKey = songKey;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getAudioDescription() {
        return audioDescription;
    }

    public void setAudioDescription(String audioDescription) {
        this.audioDescription = audioDescription;
    }

    public Long getSampleID() {
        return sampleID;
    }

    public void setSampleID(Long sampleID) {
        this.sampleID = sampleID;
    }

    public int getSamplePrice() {
        return samplePrice;
    }

    public void setSamplePrice(int samplePrice) {
        this.samplePrice = samplePrice;
    }
}
