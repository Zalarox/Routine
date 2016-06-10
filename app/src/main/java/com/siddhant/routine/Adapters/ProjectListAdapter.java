package com.siddhant.routine.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.siddhant.routine.R;
import com.siddhant.routine.classes.Project;
import com.siddhant.routine.fragments.ProjectListFragment;
import com.siddhant.routine.utilities.ProjectManager;
import com.siddhant.routine.viewholders.ProjectViewHolder;

import java.util.UUID;

/**
 * Created by Siddhant on 25-May-16.
 */

public class ProjectListAdapter extends RecyclerView.Adapter<ProjectViewHolder> {
    ProjectManager pm;
    Context context;
    ProjectListFragment host;

    public ProjectListAdapter(ProjectListFragment host) {
        this.host = host;
    }


    @Override
    public ProjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context)
                .inflate(R.layout.list_item_projects, parent, false);
        pm = ProjectManager.getInstance(context);
        return new ProjectViewHolder(this, view);
    }

    public void removeProject(UUID projectId, int position) {
        pm.deleteProject(projectId);
        notifyItemRemoved(position);
        host.updateListData();
        pm.saveData();
    }

    public void addProject(Project project) {
        pm.addProject(project);
        notifyItemInserted(pm.getSize());
        host.updateListData();
        pm.saveData();
    }

    @Override
    public void onBindViewHolder(ProjectViewHolder holder, int position) {
        pm = ProjectManager.getInstance(context);
        holder.bindProject(pm.getProject(position));
    }

    @Override
    public int getItemCount() {
        pm = ProjectManager.getInstance(context);
        return pm.getSize();
    }
}
