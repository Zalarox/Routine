package com.siddhant.routine.viewholders;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.siddhant.routine.R;
import com.siddhant.routine.adapters.ProjectListAdapter;
import com.siddhant.routine.classes.Project;
import com.siddhant.routine.fragments.ProjectEditDialogFragment;
import com.siddhant.routine.utilities.ProjectManager;

/**
 * Created by Siddhant on 25-May-16.
 */
public class ProjectViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    Project project;
    Button update;
    Button finished;
    Context context;
    ProjectListAdapter adapter;
    ProjectManager pm;
    FragmentManager fm;

    public ProjectViewHolder(ProjectListAdapter adapter, View itemView) {
        super(itemView);
        itemView.setClickable(true);
        finished = (Button) itemView.findViewById(R.id.project_finished_button);
        update = (Button) itemView.findViewById(R.id.project_edit_button);
        context = itemView.getContext();
        pm = ProjectManager.getInstance(context);
        this.adapter = adapter;
        finished.setOnClickListener(this);
        update.setOnClickListener(this);
        fm = ((AppCompatActivity)context).getSupportFragmentManager();
    }

    public void bindProject(Project project) {
        this.project = project;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.project_finished_button:
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete project")
                        .setMessage("Are you sure you want to remove this project?")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                adapter.removeProject(project.getProjectId(), getAdapterPosition());
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // no implementation required
                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();
                break;

            case R.id.project_edit_button:
                ProjectEditDialogFragment projectDialog = new ProjectEditDialogFragment();
                projectDialog.show(fm, "fragment_project");
                break;
        }
    }
}
