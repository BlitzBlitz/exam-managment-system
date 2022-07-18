package com.example.demo.entity;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class TableData {
    private SimpleIntegerProperty id;
    private SimpleStringProperty email;
    private SimpleStringProperty name;
    private SimpleStringProperty lastname;
    private SimpleStringProperty phone;

    public TableData(Teacher teacher) {
        this.id = new SimpleIntegerProperty(teacher.getId());
        this.email = new SimpleStringProperty(teacher.getEmail());
        this.name = new SimpleStringProperty(teacher.getName());
        this.lastname = new SimpleStringProperty(teacher.getLastname());
        this.phone = new SimpleStringProperty(teacher.getPhoneNumber());
    }
    public TableData(Student student) {
        this.id = new SimpleIntegerProperty(student.getId());
        this.email = new SimpleStringProperty(student.getEmail());
        this.name = new SimpleStringProperty(student.getName());
        this.lastname = new SimpleStringProperty(student.getLastname());
        this.phone = new SimpleStringProperty(student.getPhoneNumber());
    }


    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getEmail() {
        return email.get();
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getLastname() {
        return lastname.get();
    }

    public SimpleStringProperty lastnameProperty() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname.set(lastname);
    }

    public String getPhone() {
        return phone.get();
    }

    public SimpleStringProperty phoneProperty() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }
}
