package com.siddhant.routine.utilities;

import android.content.Context;

import com.siddhant.routine.classes.Course;
import com.siddhant.routine.classes.Module;
import com.siddhant.routine.classes.Project;

import java.util.ArrayList;
import java.util.UUID;

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
            module = moduleList.get(1);
        }
        return module;
    }

    public Module getCourseSuggestion() {
        Module module = null;
        ArrayList<Course> courseList = dm.getCourseList();
        if(!courseList.isEmpty()) {
            module = getModuleSuggestion(courseList.get(0));
        }
        return module;
    }

    public Project getProjectSuggestion(Course course) {
        Project project = null;
        ArrayList<UUID> projectList = course.getProjects();
        if(!projectList.isEmpty()) {
            UUID projectId = projectList.get(0);
            project = dm.getProject(projectId);
        }
        return project;
    }
}