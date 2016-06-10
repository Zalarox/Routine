package com.siddhant.routine.fragments;

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

import com.siddhant.routine.R;
import com.siddhant.routine.activities.CourseEditActivity;
import com.siddhant.routine.adapters.CourseListAdapter;
import com.siddhant.routine.classes.Course;
import com.siddhant.routine.utilities.DataManager;

/**
 * Created by Siddhant on 05-Mar-16.
 */
public class CourseListFragment extends Fragment {

    DataManager cm;
    CourseListAdapter adapter;
    RecyclerView recyclerView;
    ImageView noDataMessage;

    private void updateListData() {
        adapter.notifyDataSetChanged();
        if(cm.getCourseListSize() == 0) {
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
        cm.saveCourseData();
    }

    @Override
    public void onResume() {
        super.onResume();

        updateListData();
        cm.saveCourseData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list, container, false);

        cm = DataManager.getInstance(getContext());

        noDataMessage = (ImageView) v.findViewById(R.id.no_data_message);
        adapter = new CourseListAdapter();
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        updateListData();

        final FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Course c = new Course();
                cm.addCourse(c);
                Intent i = new Intent(getContext(), CourseEditActivity.class);
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
