package com.siddhant.routine.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.siddhant.routine.classes.Course;
import com.siddhant.routine.utilities.CourseManager;
import com.siddhant.routine.R;
import com.siddhant.routine.viewholders.CourseViewHolder;

/**
 * Created by Siddhant on 25-May-16.
 */
public class CourseListAdapter extends RecyclerView.Adapter<CourseViewHolder> {

    CourseManager cm;
    Context context;

    @Override
    public CourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        cm = CourseManager.getInstance(context);
        View view = LayoutInflater.from(context)
                .inflate(R.layout.list_item_courses, parent, false);

        return new CourseViewHolder(context, view);
    }

    @Override
    public void onBindViewHolder(CourseViewHolder holder, int position) {
        cm = CourseManager.getInstance(context);
        Course course = cm.getCourse(position);
        holder.bindCourse(course);
    }

    @Override
    public int getItemCount() {
        cm = CourseManager.getInstance(context);
        return cm.getSize();
    }
}