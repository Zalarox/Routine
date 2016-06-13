package com.siddhant.routine.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    Course course;
    Module module;
    Project project;

    ModuleCardViewHolder moduleCardViewHolder;
    ProjectViewHolder projectViewHolder;

    @Override
    public void onResume() {
        super.onResume();
//        Course course = dm.getCourse(module.getCourseId());
//        ArrayList<Module> moduleList = course.getCourseModules();
//        for(Module listModule : moduleList) {
//            if(listModule.getModuleId().equals(module.getModuleId())) {
//                module = listModule;
//            }
//        }
//        moduleCardViewHolder.bind(module);
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

        int totalCourses = dm.getCourseListSize();
        int totalProgress = (int) Math.floor(dm.getCourseTotalProgress()*100);
        totalProgressBar.setProgress(totalProgress);
        courseStats.setText(getString(R.string.dashboard_course_stats, totalProgress, totalCourses));

        if(totalCourses > 0) {
            moduleCardViewHolder = new ModuleCardViewHolder(getContext(), moduleCardView);
            course = sm.getCourseSuggestion();
            if (module != null)
                moduleCardViewHolder.bind(module);
            moduleCardView.setOnClickListener(moduleCardViewHolder);

            suggestedCourseHint.setText(getString(R.string.dashboard_next_module_hint,
                    course.getCourseName()));
        }

        int totalProjects = dm.getProjectListSize();
        if(totalProjects > 0) {
            project = sm.getProjectSuggestion();
            projectViewHolder = new ProjectViewHolder(projectCardView);
            projectViewHolder.bindProject(project);

            suggestedProjectHint.setText(getString(R.string.dashboard_assignment_text,
                    totalProjects, totalProjects));
        }

        return v;
    }
}
