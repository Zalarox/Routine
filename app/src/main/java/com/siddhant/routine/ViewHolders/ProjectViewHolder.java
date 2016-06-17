package com.siddhant.routine.viewholders;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.siddhant.routine.R;
import com.siddhant.routine.adapters.ProjectListAdapter;
import com.siddhant.routine.classes.Project;
import com.siddhant.routine.fragments.DashboardFragment;
import com.siddhant.routine.fragments.ProjectEditDialogFragment;
import com.siddhant.routine.utilities.DataManager;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.UUID;

/**
 * Created by Siddhant on 25-May-16.
 */
public class ProjectViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView projectName;
    TextView projectDueDate;
    TextView projectNotes;
    Button update;
    Button finished;

    Project project;
    Context context;
    SimpleDateFormat sdf;
    ProjectListAdapter adapter;
    DataManager dm;
    FragmentManager fm;

    public ProjectViewHolder(ProjectListAdapter adapter, View itemView) {
        super(itemView);
        itemView.setClickable(true);
        projectName = (TextView) itemView.findViewById(R.id.project_title);
        projectDueDate = (TextView) itemView.findViewById(R.id.project_due_date);
        projectNotes = (TextView) itemView.findViewById(R.id.project_notes);
        finished = (Button) itemView.findViewById(R.id.project_finished_button);
        update = (Button) itemView.findViewById(R.id.project_edit_button);

        context = itemView.getContext();
        sdf = new SimpleDateFormat("EEEE, d MMMM", Locale.US);
        dm = DataManager.getInstance(context);
        this.adapter = adapter;
        finished.setOnClickListener(this);
        update.setOnClickListener(this);
        fm = ((AppCompatActivity)context).getSupportFragmentManager();
    }

    public ProjectViewHolder(View itemView) {
        super(itemView);
        itemView.setClickable(true);
        projectName = (TextView) itemView.findViewById(R.id.project_title);
        projectDueDate = (TextView) itemView.findViewById(R.id.project_due_date);
        projectNotes = (TextView) itemView.findViewById(R.id.project_notes);
        finished = (Button) itemView.findViewById(R.id.project_finished_button);
        update = (Button) itemView.findViewById(R.id.project_edit_button);

        context = itemView.getContext();
        sdf = new SimpleDateFormat("EEEE, d MMMM", Locale.US);
        dm = DataManager.getInstance(context);

        finished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete project")
                        .setMessage("Are you sure you want to remove this project?")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(project.isLinkedCourse()) {
                                    UUID courseId = project.getCourseId();
                                    dm.getCourse(courseId).removeDue(project.getProjectId());
                                }
                                dm.deleteProject(project.getProjectId());
                                dm.saveProjectData();
                                fm = ((AppCompatActivity)context).getSupportFragmentManager();
                                DashboardFragment f = (DashboardFragment)
                                        fm.findFragmentByTag("fragment");
                                f.updateProjectCard();
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
            }
        });
        update.setOnClickListener(this);
    }

    public void bindProject(Project project) {
        this.project = project;
        projectName.setText(project.getProjectName());
        if(project.getDueDate() != null) {
            String date = sdf.format(project.getDueDate());
            projectDueDate.setText(date);
        } else {
            projectDueDate.setText(R.string.no_due_date);
        }
        projectNotes.setText(project.getNotes());
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
                                if(project.isLinkedCourse()) {
                                    UUID courseId = project.getCourseId();
                                    dm.getCourse(courseId).removeDue(project.getProjectId());
                                }
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
                Bundle args = new Bundle();
                args.putString("projectId", project.getProjectId().toString());
                projectDialog.setArguments(args);
                projectDialog.show(fm, "fragment_project");
                break;
        }
    }
}
