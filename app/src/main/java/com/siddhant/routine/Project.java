package com.siddhant.routine;

import java.util.Date;

/**
 * Created by Siddhant on 04-Mar-16.
 */
public class Project {
    private String projectName;
    private Course linkedCourse;
    private Date dueDate;
    private int priority;

    public Project(String projectName) {
        this.projectName = projectName;
    }

    public Project() {

    }
}
