package com.siddhant.routine.classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Siddhant on 04-Mar-16.
 */
public class Course implements Serializable {

    private static final String DEFAULT_COURSE_NAME = "New Course";
    private UUID courseId;
    private String courseName;
    private float courseProgress;
    private int doneModules;
    private ArrayList<Module> courseModules;

    // Constructors

    public Course() {
        this.courseId = UUID.randomUUID();
        this.courseName = DEFAULT_COURSE_NAME;
        courseModules = new ArrayList<>();
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

    public void updateProgress() {
        int total=0;
        int done=0;
        doneModules = 0;

        for(Module module : courseModules) {
            total += module.getChildItemList().size();
            done += module.getDoneTopics();
            if(module.isModuleDone())
                doneModules++;
        }

        courseProgress = (done/(float)total);
    }

    public void setModuleList(ArrayList<Module> courseModules) {
        this.courseModules = courseModules;
    }

    public int getDoneModules() {
        return doneModules;
    }

    public ArrayList<Module> getCourseModules() {
        return courseModules;
    }

}
