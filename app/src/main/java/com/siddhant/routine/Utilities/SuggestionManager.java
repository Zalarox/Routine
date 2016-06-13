package com.siddhant.routine.utilities;

import android.content.Context;

import com.siddhant.routine.classes.Course;
import com.siddhant.routine.classes.Module;
import com.siddhant.routine.classes.Project;

import java.util.ArrayList;

/**
 * Created by Siddhant on 12-Jun-16.
 */
public class SuggestionManager {

    static SuggestionManager suggestionManager;
    Context appContext;
    DataManager dm;

    private SuggestionManager(Context appContext) {
        this.appContext = appContext;
        dm = DataManager.getInstance(appContext);
    }

    public static SuggestionManager getInstance(Context c) {
        if(suggestionManager == null) {
            suggestionManager = new SuggestionManager(c.getApplicationContext());
        }
        return suggestionManager;
    }

    public Module getModuleSuggestion(Course course) {
        Module module = null;
        ArrayList<Module> moduleList = course.getCourseModules();
        if(!moduleList.isEmpty()) {
            module = moduleList.get(0);
        }
        return module;
    }

    public Course getCourseSuggestion() {
        Course course = null;
        ArrayList<Course> courseList = dm.getCourseList();
        if(!courseList.isEmpty()) {
            course = courseList.get(0);
        }
        return course;
    }

    public Project getProjectSuggestion() {
        Project project = null;
        ArrayList<Project> projectList = dm.getProjectList();
        if(!projectList.isEmpty()) {
            project = projectList.get(0);
        }
        return project;
    }
}