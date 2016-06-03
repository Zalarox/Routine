package com.siddhant.routine.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionInflater;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.siddhant.routine.R;
import com.siddhant.routine.adapters.TopicListAdapter;
import com.siddhant.routine.classes.Course;
import com.siddhant.routine.classes.Module;
import com.siddhant.routine.classes.Topic;
import com.siddhant.routine.utilities.CourseManager;

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

    TextView moduleTitle;
    TextView totalTopics;
    ProgressBar progressBar;
    RecyclerView moduleTopicList;

    public void getDataFromIntent() {
        courseId = getIntent().getStringExtra(getString(R.string.EXTRA_COURSE_UUID));
        String moduleId = getIntent().getStringExtra(getString(R.string.EXTRA_MODULE_UUID));

        CourseManager cm = CourseManager.getInstance(this);
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setSharedElementEnterTransition(TransitionInflater.from(this).
                    inflateTransition(R.transition.shared_element_transition));
        }

        setContentView(R.layout.activity_module_update);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        getDataFromIntent();

        moduleTitle = (TextView) findViewById(R.id.module_card_title);
        totalTopics = (TextView) findViewById(R.id.module_card_total_topics);
        progressBar = (ProgressBar) findViewById(R.id.module_card_progress_bar);
        moduleTopicList = (RecyclerView) findViewById(R.id.module_update_topic_list);

        topics = (ArrayList<Topic>) module.getChildItemList();

        moduleTitle.setText(getString(R.string.module_list_title, moduleNumber));
        totalTopics.setText(getString(R.string.module_topics, module.getDoneTopics(),
                topics.size()));

        TopicListAdapter adapter = new TopicListAdapter(topics, progressBar, totalTopics, module);
        moduleTopicList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        moduleTopicList.setAdapter(adapter);

        progressBar.setProgress((int) Math.floor(module.getProgress()*10000));
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent();
        i.putExtra(getString(R.string.EXTRA_COURSE_UUID), courseId);
        setResult(0, i);
        supportFinishAfterTransition();
    }
}
