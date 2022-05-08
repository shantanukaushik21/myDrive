package com.example.myDrive.Model;

import java.util.ArrayList;

public class GetFilesByUserIdResponse {
    String status;
    int fileCount;
    ArrayList<File> files;

    public GetFilesByUserIdResponse(String status, int fileCount, ArrayList<File> files) {
        this.status = status;
        this.fileCount = fileCount;
        this.files = files;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getFileCount() {
        return fileCount;
    }

    public void setFileCount(int fileCount) {
        this.fileCount = fileCount;
    }

    public ArrayList<File> getFiles() {
        return files;
    }

    public void setFiles(ArrayList<File> files) {
        this.files = files;
    }
}
