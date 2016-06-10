package com.siddhant.routine.utilities;

import android.content.Context;
import android.widget.Toast;

import com.siddhant.routine.classes.Course;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Siddhant on 15-Mar-16.
 */
public class CourseManager {
    private static CourseManager courseManager;
    private Context appContext;
    ArrayList<Course> courseList;
    JsonManager jsonManager;

    private CourseManager(Context appContext) {
        this.appContext = appContext;
        this.jsonManager = new JsonManager(appContext, "routine-c-db");
    }

    public static CourseManager getInstance(Context c) {
        if(courseManager == null) {
            courseManager = new CourseManager(c.getApplicationContext());
        }
        return courseManager;
    }

    public void addCourse(Course c) {
        courseList.add(c);
    }

    public void deleteCourse(UUID uuid) {
        for(Course course : courseList) {
            if(course.getCourseId().equals(uuid)) {
                courseList.remove(course);
                break;
            }
        }
    }

    public void updateCourse(UUID uuid, Course newCourse) {
        for(Course course : courseList) {
            if(course.getCourseId().equals(uuid)) {
                course.setCourseName(newCourse.getCourseName());
                course.setModuleList(newCourse.getCourseModules());
                course.updateProgress();
                break;
            }
        }
    }

    public Course getCourse(int position) {
        return courseList.get(position);
    }

    public ArrayList<Course> getCourseList() {
        return courseList;
    }

    public Course getCourse(UUID uuid) {
        for(Course course : courseList) {
            if(course.getCourseId().equals(uuid)) {
                return course;
            }
        }
        return null;
    }

    public int getSize() {
        return courseList.size();
    }

    public void saveData() {
        try {
            jsonManager.saveList(courseList);
        } catch (IOException e) {
            Toast.makeText(appContext, "Error saving course data...", Toast.LENGTH_SHORT).show();
        }
    }

    public void loadData() {
        try {
            courseList = jsonManager.loadCourseList();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
