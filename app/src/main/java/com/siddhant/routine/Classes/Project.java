package com.siddhant.routine.classes;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Siddhant on 04-Mar-16.
 */
public class Project implements Serializable {
    private UUID projectId;
    private UUID courseId;
    private String projectName;
    private Date dueDate;
    private String notes;
    private boolean linkedCourse;

    public Project(String projectName) {
        this.projectName = projectName;
        this.projectId = UUID.randomUUID();
    }

    public Project(String projectName, UUID courseId, Date dueDate) {
        this.projectName = projectName;
        this.courseId = courseId;
        this.dueDate = dueDate;
        this.projectId = UUID.randomUUID();
    }

    public UUID getCourseId() {
        return courseId;
    }

    public UUID getProjectId() {
        return projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setCourseId(UUID courseId) {
        this.courseId = courseId;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public boolean isLinkedCourse() {
        return linkedCourse;
    }

    public void setLinkedCourse(boolean linkedCourse) {
        this.linkedCourse = linkedCourse;
    }
}
