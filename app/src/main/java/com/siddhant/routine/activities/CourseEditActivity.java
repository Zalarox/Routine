package com.siddhant.routine.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.siddhant.routine.R;
import com.siddhant.routine.adapters.ModuleExpandableListAdapter;
import com.siddhant.routine.classes.Course;
import com.siddhant.routine.classes.Module;
import com.siddhant.routine.classes.Topic;
import com.siddhant.routine.utilities.DataManager;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Siddhant on 20-May-16.
 */
public class CourseEditActivity extends AppCompatActivity {
    DataManager cm;
    Course course;
    UUID courseId;
    EditText courseName;
    Button plusButton;
    RecyclerView expandableListView;
    ModuleExpandableListAdapter adapter;
    ArrayList<Module> moduleList;
    private long lastClickTime = 0;

    private void doBackActions() {
        course.setCourseName(courseName.getText().toString());
        moduleList = course.getCourseModules();

        ArrayList<Module> empty = new ArrayList<>();

        for(Module module : moduleList) {
            ArrayList<Topic> topicList = (ArrayList<Topic>) module.getChildItemList();
            Topic lastTopic = topicList.get(topicList.size() - 1);

            if (lastTopic.getTopicName().isEmpty())
                module.removeTopic(topicList.size() - 1);

            for (Topic topic : topicList) {
                if (topic.getTopicName().isEmpty()) {
                    module.removeTopic(topicList.indexOf(topic));
                    break;
                }
            }

            if(topicList.isEmpty())
                empty.add(module);
        }

        moduleList.removeAll(empty);

        cm.saveCourseData();
        Intent i = new Intent();
        i.putExtra(getString(R.string.EXTRA_COURSE_UUID), courseId.toString());
        setResult(0, i);
        finish();
    }

    @Override
    public void onBackPressed() {
        doBackActions();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                doBackActions();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_edit);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        courseName = (EditText) findViewById(R.id.edit_course_course_name);
        plusButton = (Button) findViewById(R.id.edit_course_plus_button);
        expandableListView = (RecyclerView) findViewById(R.id.course_edit_module_list);

        String courseIdString = getIntent().getStringExtra(getString(R.string.EXTRA_COURSE_UUID));
        courseId = UUID.fromString(courseIdString);
        cm = DataManager.getInstance(getApplicationContext());
        course = cm.getCourse(courseId);

        moduleList = course.getCourseModules();
        for(Module module : moduleList) {
            if(module.getChildItemList().isEmpty()) {
                module.addTopic();
            }
        }
        adapter = new ModuleExpandableListAdapter(this, course.getCourseModules());
        expandableListView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        expandableListView.setAdapter(adapter);

        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (SystemClock.elapsedRealtime() - lastClickTime < 300){
                    return;
                }
                lastClickTime = SystemClock.elapsedRealtime();
                adapter.addNewModule(courseId);
                expandableListView.smoothScrollToPosition(adapter.getItemCount()-1);
            }
        });

        courseName.setText(course.getCourseName());
        courseName.requestFocus();
    }
}
