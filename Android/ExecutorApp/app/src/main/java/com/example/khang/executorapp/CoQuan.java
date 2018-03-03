package com.example.khang.executorapp;

import java.io.Serializable;

/**
 * Created by khang on 02/03/2018.
 */

public class CoQuan implements Serializable {
    public String password;
    public String ten;
    public String username;
    public String key;

    public CoQuan(){

    }
    public CoQuan(String password, String ten, String username) {
        this.password = password;
        this.ten = ten;
        this.username = username;
    }
}
