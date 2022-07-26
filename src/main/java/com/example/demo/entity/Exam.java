package com.example.demo.entity;



public class Exam {


    private int id;
    private String title;
    private int course_id;


    public Exam() {
    }

    public Exam(String title, int course_id) {
        this.title = title;
        this.course_id = course_id;
    }

    public Exam(String title) {
        this.title = title;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }
}
