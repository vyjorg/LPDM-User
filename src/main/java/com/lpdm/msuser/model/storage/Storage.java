package com.lpdm.msuser.model.storage;

public class Storage {

    private int id;
    private String fileType;
    private int owner;
    private String url;

    public Storage() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Storage{" +
                "id=" + id +
                ", fileType='" + fileType + '\'' +
                ", owner=" + owner +
                ", url='" + url + '\'' +
                '}';
    }
}
