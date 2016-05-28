package com.siddhant.routine.Fragments;

import android.content.Intent;
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

import com.siddhant.routine.Activities.CourseActivity;
import com.siddhant.routine.Adapters.CourseListAdapter;
import com.siddhant.routine.Classes.Course;
import com.siddhant.routine.Utilities.CourseManager;
import com.siddhant.routine.R;

/**
 * Created by Siddhant on 05-Mar-16.
 */
public class CourseListFragment extends Fragment {

    CourseManager cm;
    CourseListAdapter recyclerViewAdapter;
    RecyclerView recyclerView;
    ImageView noDataMessage;

    private void updateListData() {
        recyclerViewAdapter.notifyDataSetChanged();
        if(cm.getSize() == 0) {
            noDataMessage.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            noDataMessage.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cm.saveData();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateListData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list, container, false);

        cm = CourseManager.getInstance(getContext());

        noDataMessage = (ImageView) v.findViewById(R.id.no_data_message);
        recyclerViewAdapter = new CourseListAdapter();

        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(recyclerViewAdapter);

        updateListData();

        final FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Course c = new Course();
                cm.addCourse(c);
                Intent i = new Intent(getContext(), CourseActivity.class);
                i.putExtra(getString(R.string.EXTRA_COURSE_UUID), c.getCourseId().toString());
                startActivity(i);
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
