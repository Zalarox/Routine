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
    private ArrayList<UUID> projects;

    // Constructors

    public Course() {
        this.courseId = UUID.randomUUID();
        this.courseName = DEFAULT_COURSE_NAME;
        courseModules = new ArrayList<>();
        projects = new ArrayList<>();
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

    public void setProjects(ArrayList<UUID> projects) {
        this.projects = projects;
    }

    public ArrayList<UUID> getProjects() {
        return projects;
    }

    public Module getModule(UUID moduleId) {
        for(Module module : courseModules) {
            if(module.getModuleId().equals(moduleId)) {
                return module;
            }
        }
        return null;
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

        if(total != 0)
            courseProgress = (done/(float)total);
    }

    public void setModuleList(ArrayList<Module> courseModules) {
        this.courseModules = courseModules;
    }

    public int getDoneModules() {
        return doneModules;
    }

    public void addDue(UUID projectId) {
        projects.add(projectId);
    }

    public void removeDue(UUID projectId) {
        projects.remove(projectId);
    }

    public int getDueProjects() { return projects.size(); }

    public ArrayList<Module> getCourseModules() {
        return courseModules;
    }

    @Override
    public String toString() {
        return courseName;
    }
}
