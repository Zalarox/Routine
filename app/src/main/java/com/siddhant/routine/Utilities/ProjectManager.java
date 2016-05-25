package com.siddhant.routine.Utilities;

import android.content.Context;
import android.widget.Toast;

import com.siddhant.routine.Classes.Project;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Siddhant on 25-May-16.
 */
public class ProjectManager {
    private static ProjectManager projectManager;
    private Context appContext;
    ArrayList<Project> projectList;
    JsonManager jsonManager;

    private ProjectManager(Context appContext) {
        this.appContext = appContext;
        jsonManager = new JsonManager(appContext, "routine-p-db");
        projectList = new ArrayList<>();
    }

    public static ProjectManager getInstance(Context c) {
        if(projectManager == null) {
            projectManager = new ProjectManager(c.getApplicationContext());
        }
        return projectManager;
    }

    public void addProject(Project p) {
        projectList.add(p);
    }

    public void deleteProject(UUID uuid) {
        for(Project project : projectList) {
            if(project.getProjectId().equals(uuid)) {
                projectList.remove(project);
                break;
            }
        }
    }

    public Project getProject(int position) {
        return projectList.get(position);
    }

    public int getSize() {
        return projectList.size();
    }

    public void saveData() {
        try {
            jsonManager.saveList(projectList);
        } catch (IOException e) {
            Toast.makeText(appContext, "Error saving project data...", Toast.LENGTH_SHORT).show();
        }
    }

    public void loadData() {
        try {
            projectList = jsonManager.loadProjectList();
        } catch (IOException e) {
            Toast.makeText(appContext, "Error saving project data...", Toast.LENGTH_SHORT).show();
        }
    }
}
