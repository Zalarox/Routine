package com.siddhant.routine.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.siddhant.routine.R;
import com.siddhant.routine.adapters.TopicListAdapter;
import com.siddhant.routine.classes.Course;
import com.siddhant.routine.classes.Module;
import com.siddhant.routine.classes.Topic;
import com.siddhant.routine.utilities.DataManager;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Siddhant on 01-Jun-16.
 */
public class ModuleUpdateActivity extends AppCompatActivity {
    String courseId;
    Module module;
    int moduleNumber;
    ArrayList<Topic> topics;
    DataManager cm;

    CardView root;
    TextView moduleTitle;
    TextView totalTopics;
    ProgressBar progressBar;
    TextView moduleDonePercent;
    RecyclerView moduleTopicList;
    ImageButton clearAll;
    ImageButton doneAll;

    TopicListAdapter adapter;

    public void getDataFromIntent() {
        courseId = getIntent().getStringExtra(getString(R.string.EXTRA_COURSE_UUID));
        String moduleId = getIntent().getStringExtra(getString(R.string.EXTRA_MODULE_UUID));

        cm = DataManager.getInstance(this);
        Course course = cm.getCourse(UUID.fromString(courseId));
        ArrayList<Module> moduleList = course.getCourseModules();
        for(Module module : moduleList) {
            if(module.getModuleId().equals(UUID.fromString(moduleId))) {
                this.module = module;
                moduleNumber = moduleList.indexOf(module)+1;
                break;
            }
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean transitionsEnabled = prefs.getBoolean("transitions", true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && transitionsEnabled) {
            getWindow().setSharedElementEnterTransition(TransitionInflater.from(this).
                    inflateTransition(R.transition.shared_element_transition));
        }

        setContentView(R.layout.activity_module_update);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        getDataFromIntent();

        moduleTitle = (TextView) findViewById(R.id.module_card_title);
        totalTopics = (TextView) findViewById(R.id.module_card_total_topics);
        moduleDonePercent = (TextView) findViewById(R.id.module_done_percent);
        progressBar = (ProgressBar) findViewById(R.id.module_card_progress_bar);
        moduleTopicList = (RecyclerView) findViewById(R.id.module_update_topic_list);
        clearAll = (ImageButton) findViewById(R.id.module_update_clear_all);
        doneAll = (ImageButton) findViewById(R.id.module_update_mark_done);
        root = (CardView) findViewById(R.id.module_update_card_view);

        topics = (ArrayList<Topic>) module.getChildItemList();

        moduleTitle.setText(getString(R.string.module_list_title, moduleNumber));
        totalTopics.setText(getString(R.string.module_topics, module.getDoneTopics(),
                topics.size()));
        adapter = new TopicListAdapter(topics, progressBar, totalTopics,
                moduleDonePercent, module);
        moduleTopicList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        moduleTopicList.setAdapter(adapter);

        float progress = module.getProgress();
        progressBar.setProgress((int) Math.floor(progress)*10000);
        moduleDonePercent.setText(getString(R.string.module_percent, (int)Math.floor(progress*100)));
        clearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(Topic topic : topics) {
                    topic.setTopicDone(false);
                }
                adapter.notifyDataSetChanged();
                cm.saveCourseData();
            }
        });

        doneAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(Topic topic : topics) {
                    topic.setTopicDone(true);
                }
                adapter.notifyDataSetChanged();
                cm.saveCourseData();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent();
        i.putExtra(getString(R.string.EXTRA_COURSE_UUID), courseId);
        setResult(0, i);
        moduleDonePercent.setVisibility(View.INVISIBLE);
        CourseActivity.adapter.notifyDataSetChanged();
        supportFinishAfterTransition();
    }
}
