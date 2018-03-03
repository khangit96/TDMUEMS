package model;

public class URLData {
    public String urlDownload;
    public String filePath;
    public String type;

    public URLData(String filePath, String type) {
        this.filePath = filePath;
        this.type = type;
    }
}