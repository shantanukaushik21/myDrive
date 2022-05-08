package com.example.myDrive.Model;

//import lombok.AllArgsConstructor;
//
//import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.*;
//@Entity
//@Table(name = "drive_user")
//@Component
public class User {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer user_id;
    private String name;
    private String email;
    private String password;
//    private int count;
//    List<File> list=new ArrayList<>();


    public User() {
    }

    public User(Integer user_id, String name, String email, String password) {
        this.user_id = user_id;
        this.name = name;
        this.email = email;
        this.password = password;
    }


    @Override
    public String toString() {
        return "User{" +
                "user_id=" + user_id +
                ", name='" + name + '\'' +
                '}';
    }

    public User(Integer user_id, String name) {
        this.user_id = user_id;
        this.name = name;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
