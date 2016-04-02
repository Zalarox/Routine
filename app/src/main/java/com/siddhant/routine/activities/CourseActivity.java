package com.siddhant.routine.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.siddhant.routine.Course;
import com.siddhant.routine.Module;
import com.siddhant.routine.R;
import com.siddhant.routine.fragments.EditCourseDialogFragment;

import java.util.ArrayList;

/**
 * Created by Siddhant on 12-Mar-16.
 */
public class CourseActivity extends AppCompatActivity {

    Course course;
    ArrayList<Module> moduleList;
    LinearLayout modulesHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        TextView titleTextView = (TextView) findViewById(R.id.activity_course_edit_text_title);

        ListView listView = (ListView) findViewById(R.id.module_topic_list_view);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1); // TODO fix this constructor call
        //listView.setAdapter(adapter);
        modulesHolder = (LinearLayout) findViewById(R.id.course_module_card_holder);
        
        course = (Course) getIntent().getSerializableExtra(getString(R.string.EXTRA_COURSE_OBJECT));
        titleTextView.setText(course.getCourseName());
        createModuleCard(1);
    }

    void createModuleCard(int moduleNumber) {
        Module m = new Module(moduleNumber, course);
        LayoutInflater inflater = getLayoutInflater();
        inflater.inflate(R.layout.card_module_view, modulesHolder);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Intent i = new Intent();
        i.putExtra(getString(R.string.EXTRA_COURSE_OBJECT), course);
        setResult(RESULT_OK, i);
    }

    public void showEditCourseDialog() {
        FragmentManager fm = getSupportFragmentManager();
        EditCourseDialogFragment editDialog = new EditCourseDialogFragment();
        editDialog.show(fm, "dialog_course_edit");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.course_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_course_edit_button:
                showEditCourseDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}