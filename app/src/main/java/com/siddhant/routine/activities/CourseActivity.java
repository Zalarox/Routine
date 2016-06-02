package com.siddhant.routine.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.siddhant.routine.adapters.ModuleCardListAdapter;
import com.siddhant.routine.classes.Course;
import com.siddhant.routine.classes.Module;
import com.siddhant.routine.R;
import com.siddhant.routine.utilities.CourseManager;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Siddhant on 12-Mar-16.
 */
public class CourseActivity extends AppCompatActivity {
    Course course;
    TextView courseTitle;
    ArrayList<Module> moduleList;
    RecyclerView moduleCardList;
    ModuleCardListAdapter adapter;
    CourseManager cm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setSharedElementExitTransition(TransitionInflater.from(this).
                            inflateTransition(R.transition.shared_element_transition));
        }
        setContentView(R.layout.activity_course);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        courseTitle = (TextView) findViewById(R.id.activity_course_title);
        cm = CourseManager.getInstance(getApplicationContext());
        String courseIdString = getIntent().getStringExtra(getString(R.string.EXTRA_COURSE_UUID));
        UUID courseId = UUID.fromString(courseIdString);

        course = cm.getCourse(courseId);
        courseTitle.setText(course.getCourseName());

        moduleList = course.getCourseModules();
        moduleCardList = (RecyclerView) findViewById(R.id.course_activity_module_list);
        moduleCardList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new ModuleCardListAdapter(moduleList);
        moduleCardList.setAdapter(adapter);
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
                Intent i = new Intent(getApplicationContext(), CourseEditActivity.class);
                i.putExtra(getString(R.string.EXTRA_COURSE_UUID), course.getCourseId().toString());
                startActivityForResult(i, 0);
                return true;

            case R.id.menu_course_delete_button:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Delete Course")
                        .setMessage("Are you sure you want to delete this course?")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                cm = CourseManager.getInstance(getApplicationContext());
                                cm.deleteCourse(course.getCourseId());
                                finish();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // no implementation required
                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();
                return true;

            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String courseIdString = data.getStringExtra(getString(R.string.EXTRA_COURSE_UUID));
        cm = CourseManager.getInstance(getApplicationContext());
        course = cm.getCourse(UUID.fromString(courseIdString));
        cm.updateCourse(course.getCourseId(), course);
        courseTitle.setText(course.getCourseName());
        moduleList = course.getCourseModules();
        adapter.notifyDataSetChanged();
    }
}