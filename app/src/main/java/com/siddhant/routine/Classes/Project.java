package com.siddhant.routine.Classes;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Siddhant on 04-Mar-16.
 */
public class Project {
    private UUID projectId;
    private UUID courseId;
    private String projectName;
    private Date dueDate;
    private int priority;

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
}
