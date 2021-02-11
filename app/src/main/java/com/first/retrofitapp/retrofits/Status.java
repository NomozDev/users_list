package com.first.retrofitapp.retrofits;

import com.first.retrofitapp.User;

import java.util.ArrayList;
import java.util.List;

public class Status {
    private  int status;
    private  String message;
    private ArrayList<User> data;
private  String id;

    public Status(int status, String message, ArrayList<User> data, String id) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public ArrayList<User> getData() {
        return data;
    }

    public String getId() {
        return id;
    }
}
