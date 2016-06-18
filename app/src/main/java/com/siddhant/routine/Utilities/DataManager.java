package com.siddhant.routine.utilities;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.siddhant.routine.classes.Course;
import com.siddhant.routine.classes.Project;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Siddhant on 15-Mar-16.
 */
public class DataManager {
    private static DataManager dataManager;
    private Context appContext;
    ArrayList<Course> courseList;
    ArrayList<Project> projectList;
    JsonManager courseJsonManager;
    JsonManager projectJsonManager;

    private DataManager(Context appContext) {
        this.appContext = appContext;
        this.courseJsonManager = new JsonManager(appContext, "routine-c-db");
        this.projectJsonManager = new JsonManager(appContext, "routine-p-db");
        courseList = new ArrayList<>();
        projectList = new ArrayList<>();
    }

    public static DataManager getInstance(Context c) {
        if(dataManager == null) {
            dataManager = new DataManager(c);
        }
        return dataManager;
    }

    public Course getCourse(UUID uuid) {
        for(Course course : courseList) {
            if(course.getCourseId().equals(uuid)) {
                return course;
            }
        }
        return null;
    }

    public void addCourse(Course c) {
        courseList.add(c);
    }

    public void deleteCourse(UUID uuid) {
        Course course = getCourse(uuid);
        courseList.remove(course);

        SharedPreferences prefs  = ((Activity)appContext).getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        String suggestedId = prefs.getString("suggestedCourse", "");
        if(course.getCourseId().equals(UUID.fromString(suggestedId))) {
            editor.putString("suggestedCourse", "");
            editor.putString("suggestedModule", "");
            editor.apply();
        }
    }

    public void updateCourse(UUID uuid, Course newCourse) {
        Course course = getCourse(uuid);
        course.setCourseName(newCourse.getCourseName());
        course.setModuleList(newCourse.getCourseModules());
        course.setProjects(newCourse.getProjects());
        course.updateProgress();
    }

    public Course getCourse(int position) {
        return courseList.get(position);
    }

    public ArrayList<Course> getCourseList() {
        return courseList;
    }

    public int getCourseListSize() {
        return courseList.size();
    }

    public float getCourseTotalProgress() {
        float progress = 0;
        for(Course course : courseList) {
            progress += course.getCourseProgress();
        }
        progress /= courseList.size();

        return progress;
    }

    public void addProject(Project project) {
        projectList.add(project);
    }

    public void deleteProject(UUID uuid) {
        Project project = getProject(uuid);
        projectList.remove(project);
    }

    public Project getProject(int position) {
        return projectList.get(position);
    }

    public Project getProject(UUID uuid) {
        for(Project project : projectList) {
            if(project.getProjectId().equals(uuid)) {
                return project;
            }
        }
        return null;
    }

    public ArrayList<Project> getProjectList() {
        return projectList;
    }

    public int getProjectListSize() {
        return projectList.size();
    }

    public int getProjectsThisWeek() {
        int total=0;

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 7);

        for(Project project: projectList) {
            if(project.getDueDate() == null) {
                total++;
            } else {
                if (project.getDueDate().before(new Date(cal.getTimeInMillis()))) {
                    total++;
                }
            }
        }

        return total;
    }

    public void updateProject(UUID uuid, Project newProject) {
        Project project = getProject(uuid);
        project.setCourseId(newProject.getCourseId());
        project.setDueDate(newProject.getDueDate());
        project.setProjectName(newProject.getProjectName());
        project.setLinkedCourse(newProject.isLinkedCourse());

        if(project.isLinkedCourse()) {
            UUID courseId = project.getCourseId();
            Course course = getCourse(courseId);
            if (project.isLinkedCourse()) {
                course.addDue(project.getProjectId());
                updateCourse(course.getCourseId(), course);
            } else {
                course.removeDue(project.getProjectId());
                updateCourse(course.getCourseId(), course);
            }
        }
    }

    public int getSize() {
        return projectList.size();
    }

    public void saveProjectData() {
        try {
            projectJsonManager.saveList(projectList);
        } catch (IOException e) {
            Toast.makeText(appContext, "Error saving project data...", Toast.LENGTH_SHORT).show();
        }
    }

    public void loadProjectData() {
        try {
            projectList = projectJsonManager.loadProjectList();
        } catch (IOException e) {
            Toast.makeText(appContext, "Error saving project data...", Toast.LENGTH_SHORT).show();
        }
    }

    public void saveCourseData() {
        try {
            courseJsonManager.saveList(courseList);
        } catch (IOException e) {
            Toast.makeText(appContext, "Error saving course data...", Toast.LENGTH_SHORT).show();
        }
    }

    public void loadCourseData() {
        try {
            courseList = courseJsonManager.loadCourseList();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}