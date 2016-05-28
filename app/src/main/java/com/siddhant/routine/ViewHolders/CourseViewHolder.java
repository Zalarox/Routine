package com.siddhant.routine.ViewHolders;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.siddhant.routine.Activities.CourseActivity;
import com.siddhant.routine.Classes.Course;
import com.siddhant.routine.R;

/**
 * Created by Siddhant on 25-May-16.
 */
public class CourseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    Course course;

    TextView title;
    TextView modules;
    TextView projects;
    ProgressBar progressBar;

    Context context;

    public CourseViewHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;

        title = (TextView) itemView.findViewById(R.id.textview_title);
        modules = (TextView) itemView.findViewById(R.id.textview_modules);
        projects = (TextView) itemView.findViewById(R.id.textview_projects);
        progressBar = (ProgressBar) itemView.findViewById(R.id.progress_bar);
        itemView.setOnClickListener(this);
    }

    public void bindCourse(Course course) {
        this.course = course;
        title.setText(course.getCourseName());
        progressBar.setProgress((int) course.getCourseProgress());
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(context, CourseActivity.class);
        i.putExtra(context.getString(R.string.EXTRA_COURSE_UUID), course.getCourseId().toString());
        context.startActivity(i);
    }
}