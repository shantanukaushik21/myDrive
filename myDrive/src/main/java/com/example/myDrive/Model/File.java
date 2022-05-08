package com.example.myDrive.Model;

import org.springframework.stereotype.Component;

//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;

public class File {

    private Integer file_id;
    private String file_name;
    private String location;

    public File(Integer file_id, String file_name, String location) {
        this.file_id = file_id;
        this.file_name = file_name;
        this.location = location;
    }

    @Override
    public String toString() {
        return "File{" +
                "file_id=" + file_id +
                ", file_name='" + file_name + '\'' +
                ", location='" + location + '\'' +
                '}';
    }

    public Integer getFile_id() {
        return file_id;
    }

    public void setFile_id(Integer file_id) {
        this.file_id = file_id;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
