package com.siddhant.routine.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.siddhant.routine.R;
import com.siddhant.routine.classes.Module;
import com.siddhant.routine.utilities.DataManager;
import com.siddhant.routine.utilities.SuggestionManager;
import com.siddhant.routine.viewholders.ModuleCardViewHolder;

/**
 * Created by Siddhant on 05-Mar-16.
 */
public class DashboardFragment extends Fragment {

    SuggestionManager sm;
    DataManager dm;
    CardView moduleCardView;

    Module module;
    ModuleCardViewHolder moduleCardViewHolder;

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
        moduleCardView = (CardView) v.findViewById(R.id.module_card_card_view);

//        moduleCardViewHolder = new ModuleCardViewHolder(getContext(), moduleCardView);
//        module = sm.getCourseSuggestion();
//        if(module != null)
//            moduleCardViewHolder.bind(module);
//        moduleCardView.setOnClickListener(moduleCardViewHolder);

        return v;
    }
}
