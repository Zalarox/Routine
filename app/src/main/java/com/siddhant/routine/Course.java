package com.siddhant.routine;

import java.util.ArrayList;

/**
 * Created by Siddhant on 04-Mar-16.
 */
public class Course {
    private static final String DEFAULT_COURSE_NAME = "New Course";
    private static final int DEFAULT_COURSE_MODULES = 4;
    private String courseName;
    // recurrence days
    private float courseProgress;
    private ArrayList<Project> courseProjects;
    private ArrayList<Module> courseModules;
    private int totalModules;
    private int doneModules;

    public Course(String courseName, float courseProgress, int doneModules) {
        this.courseName = courseName;
        this.courseProgress = courseProgress;
        this.doneModules = doneModules;
    }

    public Course() {
        this.courseName = DEFAULT_COURSE_NAME;
        this.totalModules = DEFAULT_COURSE_MODULES;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public float getCourseProgress() {
        return courseProgress;
    }

    public void setCourseProgress(float courseProgress) {
        this.courseProgress = courseProgress;
    }

    public int getTotalModules() {
        return totalModules;
    }

    public void setTotalModules(int totalModules) {
        this.totalModules = totalModules;
    }

    public int getDoneModules() {
        return doneModules;
    }

    public void setDoneModules(int doneModules) {
        this.doneModules = doneModules;
    }
}
