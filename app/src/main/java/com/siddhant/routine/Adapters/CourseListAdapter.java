package com.siddhant.routine.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.siddhant.routine.R;
import com.siddhant.routine.classes.Course;
import com.siddhant.routine.utilities.DataManager;
import com.siddhant.routine.viewholders.CourseViewHolder;

/**
 * Created by Siddhant on 25-May-16.
 */
public class CourseListAdapter extends RecyclerView.Adapter<CourseViewHolder> {

    DataManager cm;
    Context appContext;


    @Override
    public CourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        appContext = parent.getContext();
        cm = DataManager.getInstance(appContext);
        View view = LayoutInflater.from(appContext)
                .inflate(R.layout.list_item_courses, parent, false);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CourseViewHolder holder, int position) {
        cm = DataManager.getInstance(appContext);
        Course course = cm.getCourse(position);
        holder.bindCourse(course);
    }

    @Override
    public int getItemCount() {
        cm = DataManager.getInstance(appContext);
        return cm.getCourseListSize();
    }
}