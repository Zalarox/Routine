package com.siddhant.routine.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.siddhant.routine.Course;
import com.siddhant.routine.CourseManager;
import com.siddhant.routine.R;
import com.siddhant.routine.activities.CourseActivity;

/**
 * Created by Siddhant on 05-Mar-16.
 */
public class CourseListFragment extends Fragment {

    CourseManager cm;

    private class CourseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Course course;
        TextView title;
        TextView modules;
        TextView projects;
        ProgressBar progressBar;

        public CourseViewHolder(View itemView) {
            super(itemView);
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
            Intent i = new Intent(getContext(), CourseActivity.class);
            i.putExtra(getString(R.string.EXTRA_COURSE_OBJECT), course);
            startActivityForResult(i, 0);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list, container, false);

        cm = CourseManager.getInstance(getContext());

        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new CourseListAdapter());

        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Course c = new Course();
                cm.addCourse(c);
                Intent i = new Intent(getContext(), CourseActivity.class);
                startActivity(i);
            }
        });
        return v;
    }

    private class CourseListAdapter extends RecyclerView.Adapter<CourseViewHolder> {

        @Override
        public CourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_courses, parent, false);
            return new CourseViewHolder(view);
        }

        @Override
        public void onBindViewHolder(CourseViewHolder holder, int position) {
            Course course = cm.getCourse(position);
            holder.bindCourse(course);
        }

        @Override
        public int getItemCount() {
            return cm.getSize();
        }
    }
}
