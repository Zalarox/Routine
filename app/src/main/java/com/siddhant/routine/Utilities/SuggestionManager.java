package com.siddhant.routine.utilities;

import android.content.Context;

import com.siddhant.routine.classes.Course;
import com.siddhant.routine.classes.Module;
import com.siddhant.routine.classes.Project;

import java.util.ArrayList;
import java.util.Random;

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

    public Course getRandomCourse(ArrayList<Course> courseList, Course course) {

        ArrayList<Course> notDone = new ArrayList<>();

        for(Course courseIte : courseList) {
            if(courseIte.getCourseProgress() != 1.00) {
                notDone.add(courseIte);
            }
        }

        if(notDone.contains(course)) {
            notDone.remove(course);
        }

        Random r = new Random();
        course = notDone.get(r.nextInt(notDone.size()));
        notDone.clear();
        return course;
    }

    public Course getCourseSuggestion(Course course) {
        ArrayList<Course> courseList = dm.getCourseList();
        if(!courseList.isEmpty()) {
            course = getRandomCourse(courseList, course);
        }

        return course;
    }

    public Module getModuleSuggestion(Course course, Module module) {

        ArrayList<Module> moduleList = course.getCourseModules();
        ArrayList<Module> notDone = new ArrayList<>();

        if(!moduleList.isEmpty()) {
            for(Module moduleIte : moduleList) {
                if(moduleIte.getDoneTopics() != moduleIte.getChildItemList().size()) {
                    notDone.add(moduleIte);
                }
            }

            if(notDone.contains(module))
                notDone.remove(module);

            if(!notDone.isEmpty())
                module = notDone.get(0);
        }

        notDone.clear();
        return module;
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