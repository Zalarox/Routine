package com.siddhant.routine.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.siddhant.routine.R;
import com.siddhant.routine.classes.Project;
import com.siddhant.routine.fragments.ProjectListFragment;
import com.siddhant.routine.utilities.DataManager;
import com.siddhant.routine.viewholders.ProjectViewHolder;

import java.util.UUID;

/**
 * Created by Siddhant on 25-May-16.
 */

public class ProjectListAdapter extends RecyclerView.Adapter<ProjectViewHolder> {
    DataManager dm;
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
        dm = DataManager.getInstance(context);
        return new ProjectViewHolder(this, view);
    }

    public void removeProject(UUID projectId, int position) {
        dm.deleteProject(projectId);
        notifyItemRemoved(position);
        host.updateListData();
        dm.saveProjectData();
    }

    public void addProject(Project project) {
        dm.addProject(project);
        notifyItemInserted(dm.getSize());
        host.updateListData();
        dm.saveProjectData();
    }

    @Override
    public void onBindViewHolder(ProjectViewHolder holder, int position) {
        dm = DataManager.getInstance(context);
        holder.bindProject(dm.getProject(position));
    }

    @Override
    public int getItemCount() {
        dm = DataManager.getInstance(context);
        return dm.getSize();
    }
}
