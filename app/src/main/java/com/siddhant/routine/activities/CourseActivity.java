package com.siddhant.routine.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.siddhant.routine.Classes.Course;
import com.siddhant.routine.Utilities.CourseManager;
import com.siddhant.routine.Classes.Module;
import com.siddhant.routine.R;

import java.util.ArrayList;

/**
 * Created by Siddhant on 12-Mar-16.
 */
public class CourseActivity extends AppCompatActivity {

    Course course;
    ArrayList<Module> moduleList;
    LinearLayout modulesHolder;
    CourseManager cm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        course = (Course) getIntent().getSerializableExtra(getString(R.string.EXTRA_COURSE_OBJECT));

        moduleList = course.getCourseModules();
        modulesHolder = (LinearLayout) findViewById(R.id.course_module_card_holder);

        for (Module module : moduleList) {
            createModuleCard(module);
        }

    }

    void createModuleCard(Module module) {
        LayoutInflater inflater = getLayoutInflater();
        inflater.inflate(R.layout.card_module_view, modulesHolder);
        TextView noTopics = (TextView) findViewById(R.id.module_has_no_topics);

        ListView listView = (ListView) findViewById(R.id.module_topic_list_view);
        // ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
        //                                                             module.getTopics());
        //listView.setAdapter(adapter);

        if(module.getChildItemList().isEmpty()) {
            noTopics.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
            getResources().getString(R.string.module_topics, 0, 0);
        } else {
            noTopics.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            getResources().getString(R.string.module_topics, 0, 0); //TODO add format topics
        }

        Button updateButton = (Button) findViewById(R.id.module_update_button);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // topic management dialog here
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.course_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_course_edit_button:
                Intent i = new Intent(getApplicationContext(), CourseEditActivity.class);
                i.putExtra(getString(R.string.EXTRA_COURSE_OBJECT), course);
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
}