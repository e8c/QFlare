package com.example.emilychou.qflare;

/**
 * Created by emilychou on 7/7/18.
 */

public class User {
    private String name;
    private String phoneNumber;
    private String position;
    private String room;
    private String email;

    User(){}
    User(String name, String room, String phoneNumber, String position, String email){
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.position = position;
        this.room = room;
        this.email = email;
    }

    public String getName(){
        return name;
    }

    public String getPhoneNumber(){
        return phoneNumber;
    }

    public String getPosition(){
        return position;
    }

    public String getRoom(){
        return room;
    }

    public String getEmail(){
        return email;
    }
}
