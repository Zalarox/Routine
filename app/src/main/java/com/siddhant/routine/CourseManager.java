package com.siddhant.routine;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by Siddhant on 15-Mar-16.
 */
public class CourseManager {
    private static CourseManager courseManager;
    private Context appContext;
    ArrayList<Course> courseList;

    private CourseManager(Context appContext) {
        this.appContext = appContext;
        // load data or create new here

        // Dummy Data code
        courseList = new ArrayList<Course>();
        for(int i=0; i<10; i++) {
            courseList.add(new Course(("My Course " + i), (10 + i*10), 4));
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

    public void deleteCourse(Course c) {
        courseList.remove(c);
    }

    public Course getCourse(int position) {
        return courseList.get(position);
    }

    public int getSize() {
        return courseList.size();
    }

    public void saveData() {
        // serialise all data
    }

    public void loadData() {
        // load all data
    }
}
