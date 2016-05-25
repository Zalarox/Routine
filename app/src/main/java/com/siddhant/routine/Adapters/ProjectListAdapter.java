package com.siddhant.routine.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.siddhant.routine.R;
import com.siddhant.routine.Utilities.ProjectManager;
import com.siddhant.routine.ViewHolders.ProjectViewHolder;

/**
 * Created by Siddhant on 25-May-16.
 */
public class ProjectListAdapter extends RecyclerView.Adapter<ProjectViewHolder> {
    ProjectManager pm;
    Context context;

    @Override
    public ProjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context)
                .inflate(R.layout.list_item_projects, parent, false);
        pm = ProjectManager.getInstance(context);
        return new ProjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProjectViewHolder holder, int position) {
        pm = ProjectManager.getInstance(context);
    }

    @Override
    public int getItemCount() {
        pm = ProjectManager.getInstance(context);
        return pm.getSize();
    }
}
