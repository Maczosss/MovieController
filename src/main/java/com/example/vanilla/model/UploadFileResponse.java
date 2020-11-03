package com.example.vanilla.model;

public class UploadFileResponse {

    private String filename;
    private String fileType;
    double size;

    public UploadFileResponse(String filename, String fileType, double size) {
        this.filename = filename;
        this.fileType = fileType;
        this.size = size;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }
}
