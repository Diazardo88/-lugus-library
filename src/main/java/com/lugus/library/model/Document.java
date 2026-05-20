package com.lugus.library.model;

public class Document {
    private int id;
    private String title;
    private String author;
    private String info;
    private String filename;
    private int uploadedBy;
    private String uploaderName;
    private String uploaderEmail;
    private String createdAt;

    public Document() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public String getInfo() { return info; }
    public void setInfo(String info) { this.info = info; }
    public String getFilename() { return filename; }
    public void setFilename(String filename) { this.filename = filename; }
    public int getUploadedBy() { return uploadedBy; }
    public void setUploadedBy(int uploadedBy) { this.uploadedBy = uploadedBy; }
    public String getUploaderName() { return uploaderName; }
    public void setUploaderName(String uploaderName) { this.uploaderName = uploaderName; }
    public String getUploaderEmail() { return uploaderEmail; }
    public void setUploaderEmail(String uploaderEmail) { this.uploaderEmail = uploaderEmail; }
    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
}
