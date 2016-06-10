package com.siddhant.routine.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.siddhant.routine.R;
import com.siddhant.routine.adapters.ProjectListAdapter;
import com.siddhant.routine.classes.Project;
import com.siddhant.routine.utilities.DataManager;

/**
 * Created by Siddhant on 05-Mar-16.
 */
public class ProjectListFragment extends Fragment {

    DataManager dm;
    ProjectListAdapter adapter;
    ImageView noDataMessage;
    RecyclerView recyclerView;

    public void updateListData() {
        if(dm.getSize() == 0) {
            noDataMessage.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            noDataMessage.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
        dm.saveProjectData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list, container, false);
        dm = DataManager.getInstance(getContext());

        noDataMessage = (ImageView) v.findViewById(R.id.no_data_message);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);

        adapter = new ProjectListAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        updateListData();

        final FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Project p = new Project("My Project");
                adapter.addProject(p);

            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy){
                if (dy > 0)
                    fab.hide();
                else if (dy < 0)
                    fab.show();
            }
        });

        return v;
    }
}
