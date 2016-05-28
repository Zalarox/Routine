package com.siddhant.routine.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.siddhant.routine.Adapters.ModuleExpandableListAdapter;
import com.siddhant.routine.Classes.Course;
import com.siddhant.routine.R;

import java.util.UUID;

/**
 * Created by Siddhant on 20-May-16.
 */
public class CourseEditActivity extends AppCompatActivity implements View.OnClickListener {

    Course course;
    UUID courseId;
    EditText courseName;
    Button plusButton;
    RecyclerView expandableListView;
    ModuleExpandableListAdapter adapter;

    public static int clamp(float val, float min, float max) {
        return (int) Math.max(min, Math.min(max, val));
    }

    private void doBackActions() {
        course.setCourseName(courseName.getText().toString());
        Intent i = new Intent();
        i.putExtra(getString(R.string.EXTRA_COURSE_OBJECT), course);
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

        course = (Course) getIntent().getSerializableExtra
                (getString(R.string.EXTRA_COURSE_OBJECT));
        courseId = course.getCourseId();

        adapter = new ModuleExpandableListAdapter(this, course.getCourseModules());
        expandableListView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        expandableListView.setAdapter(adapter);

        plusButton.setOnClickListener(this);

        courseName.setText(course.getCourseName());
        courseName.requestFocus();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.edit_course_plus_button:
                adapter.addNewModule(courseId);
                expandableListView.scrollToPosition(course.getCourseModules().size()-1);
                break;

            case R.id.expandable_list_module_delete:
                break;

            default: break;
        }
    }
}
