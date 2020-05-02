package ch.zhaw.it.pm2.professor.controller;

public class Room {
    String name;
    boolean isDone;
    QuestionGenerator questions;

    public Room(String name){
        this.name=name;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public QuestionGenerator getQuestions() {
        return questions;
    }

    public void setQuestions(QuestionGenerator questions) {
        this.questions = questions;
    }

}
