package com.siddhant.routine.fragments;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
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

import java.util.Calendar;
import java.util.Date;
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
    Button projectButton;

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

    public void setNoCourses() {
        moduleCardView.setVisibility(View.GONE);
        courseButton.setEnabled(false);
        moduleButton.setEnabled(false);
        suggestedCourseHint.setText("No unfinished courses found...");
        suggestedCourseHint.setMinimumHeight
                ((int)(150 * getResources().getDisplayMetrics().density));

        SharedPreferences prefs  = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        module = null;
        course = null;
        editor.putString("suggestedCourse", "");
        editor.putString("suggestedModule", "");
        editor.apply();
    }

    public void setNoProjects() {
        projectCardView.setVisibility(View.GONE);
        suggestedProjectHint.setText("No projects found...");

        SharedPreferences prefs  = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        project = null;
        editor.putString("suggestedProject", "");
        editor.apply();
    }

    public void bindModuleCard() {
        if(module.getDoneTopics() != module.getChildItemList().size()) {
            moduleCardViewHolder.bind(module);
            suggestedCourseHint.setMinimumHeight(0);
            suggestedCourseHint.setText(Html.fromHtml(getString(R.string.dashboard_next_module_hint,
                    course.getCourseName())));
        } else {
            setNoCourses();
        }

        int totalProgress = (int) Math.floor(dm.getCourseTotalProgress()*100);
        ObjectAnimator animation = ObjectAnimator.ofInt(totalProgressBar, "progress",
                totalProgress);
        animation.setDuration(500);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();
        courseStats.setText(Html.fromHtml(getString(
                R.string.dashboard_course_stats, totalProgress, totalCourses)));
    }

    public void bindProjectCard() {
        if(project != null) {
            projectViewHolder.bindProject(project);
            int totalThisWeek = dm.getProjectsThisWeek();
            suggestedProjectHint.setText(Html.fromHtml(getString(R.string.dashboard_assignment_text,
                totalThisWeek, totalProjects)));
        } else {
            setNoProjects();
        }
    }

    public void initSuggestions() {
        SharedPreferences prefs = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        totalProjects = dm.getProjectListSize();

        if (totalCourses == 0) {
            editor.putString("suggestedCourse", "");
            editor.putString("suggestedModule", "");
        }
        if (totalProjects == 0) {
            editor.putString("suggestedProject", "");
        }
        editor.apply();

        String courseId = prefs.getString("suggestedCourse", "");
        String moduleId = prefs.getString("suggestedModule", "");
        String projectId = prefs.getString("suggestedProject", "");

        if (courseId.isEmpty() || moduleId.isEmpty()) {
            updateModuleCard(true);
        } else {
            if (totalCourses > 0) {
                course = dm.getCourse(UUID.fromString(courseId));
                module = course.getModule(UUID.fromString(moduleId));
                bindModuleCard();
            }
        }
        if (projectId.isEmpty()) {
            updateProjectCard();
        } else {
            if (totalProjects > 0) {
                project = dm.getProject(UUID.fromString(projectId));

                if (project != null && project.getDueDate() == null) {
                    bindProjectCard();
                } else if (project != null) {
                    Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.DAY_OF_MONTH, 7);
                    if (project.getDueDate().before(new Date(cal.getTimeInMillis())))
                        bindProjectCard();
                    else
                        updateProjectCard();
                } else {
                    setNoProjects();
                }
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
            editor.apply();
        } else {
            setNoCourses();
        }
    }

    public void updateProjectCard() {
        SharedPreferences prefs  = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        totalProjects = dm.getProjectListSize();

        if(totalProjects > 0)
            project = sm.getProjectSuggestion(project);

        if(totalProjects > 0 && project != null) {
            bindProjectCard();
            projectCardView.setVisibility(View.VISIBLE);
            editor.putString("suggestedProject", project.getProjectId().toString());
        } else {
            setNoProjects();
        }
        editor.apply();
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
        projectButton = (Button) v.findViewById(R.id.project_next_button);

        projectViewHolder = new ProjectViewHolder(projectCardView);
        moduleCardViewHolder = new ModuleCardViewHolder(getContext(), moduleCardView);
        moduleCardView.setOnClickListener(moduleCardViewHolder);

        totalCourses = dm.getCourseListSize();
        int totalProgress = (int) Math.floor(dm.getCourseTotalProgress()*100);
        totalProgressBar.setProgress(totalProgress);
        courseStats.setText(Html.fromHtml(getString(
                R.string.dashboard_course_stats, totalProgress, totalCourses)));

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
        projectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProjectCard();
            }
        });
        projectButton.setVisibility(View.VISIBLE);

        initSuggestions();
        firstRun = false;
        return v;
    }
}