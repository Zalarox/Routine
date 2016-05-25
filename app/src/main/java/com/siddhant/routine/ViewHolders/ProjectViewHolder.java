package com.siddhant.routine.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.siddhant.routine.Classes.Project;

/**
 * Created by Siddhant on 25-May-16.
 */
public class ProjectViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

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
