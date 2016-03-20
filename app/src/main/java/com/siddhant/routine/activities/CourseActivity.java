package com.siddhant.routine.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.siddhant.routine.Course;
import com.siddhant.routine.Module;
import com.siddhant.routine.R;

/**
 * Created by Siddhant on 12-Mar-16.
 */
public class CourseActivity extends AppCompatActivity {

    Course course;
    Module module;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        EditText titleTextView = (EditText) findViewById(R.id.activity_course_edit_text_title);

        ListView listView = (ListView) findViewById(R.id.module_topic_list_view);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1); // TODO fix this constructor call
        //listView.setAdapter(adapter);

        course = (Course) getIntent().getSerializableExtra(getString(R.string.EXTRA_COURSE_OBJECT));
        titleTextView.setText(course.getCourseName());
    }
}
