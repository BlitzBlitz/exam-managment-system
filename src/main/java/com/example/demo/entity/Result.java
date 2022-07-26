package com.example.demo.entity;


public class Result {
    private Student student;
    private Exam exam;
    private double result;


    public Result() {
    }

    public Result(Student student, Exam exam, int result) {
        this.student = student;
        this.exam = exam;
        this.result = result;

    }


    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }

}
