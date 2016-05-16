package com.siddhant.routine;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Siddhant on 04-Mar-16.
 */
public class Course implements Serializable {

    private static final String DEFAULT_COURSE_NAME = "New Course";
    private static final int DEFAULT_COURSE_MODULES = 2;

    private UUID courseId;
    private String courseName;
    private float courseProgress;
    private int totalModules;
    private int doneModules;

    // TODO add recurrence days

    private ArrayList<Module> courseModules;

    // Constructors

    public Course(String courseName, float courseProgress, int doneModules) {
        this.courseId = UUID.randomUUID();
        this.courseName = courseName;
        this.courseProgress = courseProgress;
        this.doneModules = doneModules;
    }

    public Course() {
        this.courseId = UUID.randomUUID();
        this.courseName = DEFAULT_COURSE_NAME;
        this.totalModules = DEFAULT_COURSE_MODULES;
        for(int i=0; i<DEFAULT_COURSE_MODULES; i++) {
            courseModules.add(new Module(i, courseId));
        }
    }

    // Getters and Setters

    public UUID getCourseId() {
        return courseId;
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

    public ArrayList<Module> getCourseModules() {
        return courseModules;
    }
}
