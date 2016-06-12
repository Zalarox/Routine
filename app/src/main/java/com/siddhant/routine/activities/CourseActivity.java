package com.siddhant.routine.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.siddhant.routine.R;
import com.siddhant.routine.adapters.ModuleCardListAdapter;
import com.siddhant.routine.classes.Course;
import com.siddhant.routine.classes.Module;
import com.siddhant.routine.utilities.DataManager;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Siddhant on 12-Mar-16.
 */
public class CourseActivity extends AppCompatActivity {
    Course course;
    FrameLayout frameLayout;
    TextView courseTitle;
    ArrayList<Module> moduleList;
    RecyclerView moduleCardList;
    static ModuleCardListAdapter adapter;
    DataManager cm;

    void initTheme() {
        SharedPreferences pref = PreferenceManager
                .getDefaultSharedPreferences(this);
        String themeName = pref.getString("theme", "0");
        switch(themeName) {
            case "0":
                setTheme(R.style.AppTheme_NoActionBar);
                break;
            case "1":
                setTheme(R.style.AppTheme_Hulk);
                break;
            case "2":
                setTheme(R.style.AppTheme_Wolverine);
                break;
            case "3":
                setTheme(R.style.AppTheme_Batman);
                break;
            case "4":
                setTheme(R.style.AppTheme_Daredevil);
                break;
            case "5":
                setTheme(R.style.AppTheme_GreenArrow);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        initTheme();
        super.onCreate(savedInstanceState);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean transitionsEnabled = prefs.getBoolean("transitions", true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && transitionsEnabled) {
            getWindow().setSharedElementExitTransition(TransitionInflater.from(this).
                            inflateTransition(R.transition.shared_element_transition));
        }

        setContentView(R.layout.activity_course);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        frameLayout = (FrameLayout) findViewById(R.id.activity_course_header);

        courseTitle = (TextView) findViewById(R.id.activity_course_title);
        cm = DataManager.getInstance(getApplicationContext());
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
                                cm = DataManager.getInstance(getApplicationContext());
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
        cm = DataManager.getInstance(getApplicationContext());
        course = cm.getCourse(UUID.fromString(courseIdString));
        cm.updateCourse(course.getCourseId(), course);
        courseTitle.setText(course.getCourseName());
        moduleList = course.getCourseModules();
    }
}