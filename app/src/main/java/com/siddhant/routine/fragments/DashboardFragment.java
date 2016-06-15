package com.siddhant.routine.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.siddhant.routine.R;
import com.siddhant.routine.classes.Course;
import com.siddhant.routine.classes.Module;
import com.siddhant.routine.classes.Project;
import com.siddhant.routine.utilities.DataManager;
import com.siddhant.routine.utilities.SuggestionManager;
import com.siddhant.routine.viewholders.ModuleCardViewHolder;
import com.siddhant.routine.viewholders.ProjectViewHolder;

import java.util.UUID;

/**
 * Created by Siddhant on 05-Mar-16.
 */
public class DashboardFragment extends Fragment {

    SuggestionManager sm;
    DataManager dm;
    CardView moduleCardView;
    CardView projectCardView;

    TextView courseStats;
    TextView suggestedCourseHint;
    ProgressBar totalProgressBar;
    TextView suggestedProjectHint;

    Button courseButton;
    Button moduleButton;

    Course course;
    Module module;
    Project project;
    int totalProjects;
    int totalCourses;

    ModuleCardViewHolder moduleCardViewHolder;
    ProjectViewHolder projectViewHolder;

    boolean firstRun = true;

    @Override
    public void onResume() {
        super.onResume();
        if(!firstRun)
            initSuggestions();
    }

    public void bindModuleCard() {
        moduleCardViewHolder.bind(module);
        suggestedCourseHint.setText(getString(R.string.dashboard_next_module_hint,
                course.getCourseName()));
    }

    public void bindProjectCard() {
        projectViewHolder.bindProject(project);
        suggestedProjectHint.setText(getString(R.string.dashboard_assignment_text,
                totalProjects, totalProjects));
    }

    public void initSuggestions() {
        SharedPreferences prefs  = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        totalProjects = dm.getProjectListSize();

        if(totalCourses == 0) {
            editor.putString("suggestedCourse", "");
            editor.putString("suggestedModule", "");
        }
        if(totalProjects == 0) {
            editor.putString("suggestedProject", "");
        }
        editor.apply();

        String courseId = prefs.getString("suggestedCourse", "");
        String moduleId = prefs.getString("suggestedModule", "");
        String projectId = prefs.getString("suggestedProject", "");


        if(courseId.isEmpty() || moduleId.isEmpty() || projectId.isEmpty()) {
            updateSuggestions();
        } else {
            if(totalCourses > 0) {
                course = dm.getCourse(UUID.fromString(courseId));
                module = course.getModule(UUID.fromString(moduleId));
                bindModuleCard();
            }
            if(totalProjects > 0) {
                project = dm.getProject(UUID.fromString(projectId));
                bindProjectCard();
            }
        }
    }

    public void updateModuleCard(boolean changeCourse) {
        SharedPreferences prefs  = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        totalCourses = dm.getCourseListSize();
        if(totalCourses > 0) {
            if(changeCourse)
                course = sm.getCourseSuggestion(course);

            if(course != null)
                module = sm.getModuleSuggestion(course, module);
            else
                module = null;
        }

        if(module != null && totalCourses > 0) {
            bindModuleCard();
            moduleCardView.setVisibility(View.VISIBLE);
            courseButton.setEnabled(true);
            moduleButton.setEnabled(true);
            editor.putString("suggestedCourse", course.getCourseId().toString());
            editor.putString("suggestedModule", module.getModuleId().toString());
        } else {
            moduleCardView.setVisibility(View.GONE);
            courseButton.setEnabled(false);
            moduleButton.setEnabled(false);
            suggestedCourseHint.setText("No courses found...");
            suggestedCourseHint.setMinimumHeight
                    ((int)(150 * getResources().getDisplayMetrics().density));
        }

        editor.apply();
    }

    public void updateProjectCard() {
        SharedPreferences prefs  = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        totalProjects = dm.getProjectListSize();
        if(totalProjects > 0) {
            project = sm.getProjectSuggestion();
            bindProjectCard();
            projectCardView.setVisibility(View.VISIBLE);
            editor.putString("suggestedProject", project.getProjectId().toString());
        } else {
            projectCardView.setVisibility(View.GONE);
            suggestedProjectHint.setText("No projects found...");
        }

        editor.apply();
    }

    public void updateSuggestions() {
        updateModuleCard(true);
        updateProjectCard();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dashboard, container, false);

        sm = SuggestionManager.getInstance(getContext());
        dm = DataManager.getInstance(getContext());
        courseStats = (TextView) v.findViewById(R.id.dashboard_stats);
        moduleCardView = (CardView) v.findViewById(R.id.module_card_card_view);
        totalProgressBar = (ProgressBar) v.findViewById(R.id.total_progress_bar);
        suggestedCourseHint = (TextView) v.findViewById(R.id.dashboard_next_course_hint);

        projectCardView = (CardView) v.findViewById(R.id.project_card);
        suggestedProjectHint = (TextView) v.findViewById(R.id.dashboard_assignment_text);

        courseButton = (Button) v.findViewById(R.id.dashboard_course_button);
        moduleButton = (Button) v.findViewById(R.id.dashboard_module_button);

        projectViewHolder = new ProjectViewHolder(projectCardView);
        moduleCardViewHolder = new ModuleCardViewHolder(getContext(), moduleCardView);
        moduleCardView.setOnClickListener(moduleCardViewHolder);

        totalCourses = dm.getCourseListSize();
        int totalProgress = (int) Math.floor(dm.getCourseTotalProgress()*100);
        totalProgressBar.setProgress(totalProgress);
        courseStats.setText(getString(R.string.dashboard_course_stats, totalProgress, totalCourses));

        courseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateModuleCard(true);
            }
        });

        moduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateModuleCard(false);
            }
        });

        initSuggestions();
        firstRun = false;
        return v;
    }
}