package com.siddhant.routine.utilities;

import android.content.Context;

import com.siddhant.routine.classes.Course;
import com.siddhant.routine.classes.Module;
import com.siddhant.routine.classes.Project;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * Created by Siddhant on 12-Jun-16.
 */
public class SuggestionManager {

    static SuggestionManager suggestionManager;
    Context appContext;
    DataManager dm;
    int currentMod, currentProj;

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
            if(courseIte.getCourseProgress() != 1.00 && !courseIte.getCourseModules().isEmpty()) {
                notDone.add(courseIte);
            }
        }

        if(notDone.contains(course)) {
            notDone.remove(course);
        }

        Random r = new Random();
        if(!notDone.isEmpty())
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

            if(!notDone.isEmpty())
                module = notDone.get(currentMod++);

            if(currentMod > notDone.size()-1)
                currentMod = 0;
        }

        notDone.clear();
        return module;
    }

    public Project getProjectSuggestion(Project project) {
        ArrayList<Project> projectList = dm.getProjectList();
        ArrayList<Project> curatedList = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 7);
        Date nextWeek = new Date(cal.getTimeInMillis());

        if(!projectList.isEmpty()) {
            for (Project projectIte : projectList) {
                if (projectIte.getDueDate() == null || projectIte.getDueDate().before(nextWeek)) {
                    curatedList.add(projectIte);
                }
            }

            if(!curatedList.isEmpty())
                project = curatedList.get(currentProj++);

            if(currentProj > curatedList.size()-1)
                currentProj = 0;
        }

        curatedList.clear();
        return project;
    }
}