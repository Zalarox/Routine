package com.siddhant.routine;


/**
 * Created by Siddhant on 04-Mar-16.
 */
public class Module {
    private int moduleNumber;
    private Course moduleCourse;
    private int doneTopics;
    private int totalTopics;
    private boolean isDone;

    public Module(int moduleNumber, Course moduleCourse) {
        this.moduleNumber = moduleNumber;
        this.moduleCourse = moduleCourse;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setIsDone(boolean isDone) {
        this.isDone = isDone;
    }

    public int getProgress() {
        return doneTopics;
    }

    public void setProgress(int doneTopics) {
        this.doneTopics = doneTopics;
    }

    public Course getModuleCourse() {
        return moduleCourse;
    }

    public void setModuleCourse(Course moduleCourse) {
        this.moduleCourse = moduleCourse;
    }

    public int getModuleNumber() {
        return moduleNumber;
    }

    public void setModuleNumber(int moduleNumber) {
        this.moduleNumber = moduleNumber;
    }
}
