package com.siddhant.routine.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.siddhant.routine.Project;
import com.siddhant.routine.R;

import java.util.ArrayList;

/**
 * Created by Siddhant on 05-Mar-16.
 */
public class ProjectListFragment extends Fragment {

    ArrayList<Project> projectList;

    private class ProjectViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public ProjectViewHolder(View itemView) {
            super(itemView);

            itemView.setClickable(true);
        }

        public void bindProject(Project project) {

        }

        @Override
        public void onClick(View v) {

        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list, container, false);


        // Dummy Data code
        projectList = new ArrayList<Project>();
        for(int i=0; i<10; i++) {
            projectList.add(new Project());
        }

        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new ProjectListAdapter());

        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Add a new project", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        return v;
    }

    private class ProjectListAdapter extends RecyclerView.Adapter<ProjectViewHolder> {

        @Override
        public ProjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_projects, parent, false);

            return new ProjectViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ProjectViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return projectList.size();
        }
    }
}
