package com.example.myDrive.Model;

public class FileUploadResponse {
    String Authorisation_status;
    String File_upload_status;
    String fileLocation;

    public FileUploadResponse(String authorisation_status, String file_upload_status, String fileLocation) {
        Authorisation_status = authorisation_status;
        File_upload_status = file_upload_status;
        this.fileLocation = fileLocation;
    }

    public String getAuthorisation_status() {
        return Authorisation_status;
    }

    public void setAuthorisation_status(String authorisation_status) {
        Authorisation_status = authorisation_status;
    }

    public String getFile_upload_status() {
        return File_upload_status;
    }

    public void setFile_upload_status(String file_upload_status) {
        File_upload_status = file_upload_status;
    }

    public String getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }
}
