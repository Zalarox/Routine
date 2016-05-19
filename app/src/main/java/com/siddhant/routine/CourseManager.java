package com.siddhant.routine;

import android.content.Context;
import android.widget.Toast;

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
        // load data or create new here...
        jsonManager = new JsonManager(appContext, "routine-db");

        // Dummy Data code
        courseList = new ArrayList<Course>();
        for(int i=0; i<3; i++) {
            courseList.add(new Course());
        }
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

    public Course getCourse(int position) {
        return courseList.get(position);
    }

    public int getSize() {
        return courseList.size();
    }

    public void saveData() {
        try {
            jsonManager.saveCourses(courseList);
        } catch (IOException e) {
            Toast.makeText(appContext, "Error saving data...", Toast.LENGTH_SHORT).show();
        }
    }

    public void loadData() {
        try {
            courseList = jsonManager.loadCourses();
        } catch (IOException e) {
            Toast.makeText(appContext, "Error loading data...", Toast.LENGTH_SHORT).show();
        }
    }
}
