package com.example.demo.entity;

public class Teacher {
    private int id;
    private String email;
    private String password;
    private String name;
    private String lastname;
    private String phoneNumber;

    public Teacher() {
    }

    public Teacher(String email, String password, String name, String lastname, String phoneNumber) {

        this.email = email;
        this.password = password;
        this.name = name;
        this.lastname = lastname;
        this.phoneNumber = phoneNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
