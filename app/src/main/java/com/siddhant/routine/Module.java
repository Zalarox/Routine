package com.siddhant.routine;


/**
 * Created by Siddhant on 04-Mar-16.
 */
public class Module {
    private int moduleNumber;
    private Course moduleCourse;
    private int progress;
    private boolean isDone;

    public boolean isDone() {
        return isDone;
    }

    public void setIsDone(boolean isDone) {
        this.isDone = isDone;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
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
