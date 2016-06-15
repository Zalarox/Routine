package com.siddhant.routine.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.siddhant.routine.R;
import com.siddhant.routine.classes.Course;
import com.siddhant.routine.fragments.CourseListFragment;
import com.siddhant.routine.fragments.DashboardFragment;
import com.siddhant.routine.fragments.ProjectEditDialogFragment;
import com.siddhant.routine.fragments.ProjectListFragment;
import com.siddhant.routine.utilities.DataManager;

import java.util.UUID;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener,
        ProjectEditDialogFragment.OnProjectDialogCloseListener {

    Toolbar toolbar;
    NavigationView navigationView;
    DataManager dm;
    FragmentManager fm = getSupportFragmentManager();
    public static String themeName;

    @Override
    protected void onResume() {
        super.onResume();
    }

    void initTheme() {
        SharedPreferences pref = PreferenceManager
                .getDefaultSharedPreferences(this);
        themeName = pref.getString("theme", "0");

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
    protected void onCreate(Bundle savedInstanceState) {
//        initTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(toolbar != null)
            toolbar.setTitle("Dashboard");
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        if(drawer!=null)
            drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        if(navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this);
            navigationView.getMenu().getItem(0).setChecked(true);
        }

        dm = DataManager.getInstance(this);
        dm.loadCourseData();
        dm.loadProjectData();

        new CourseListFragment();
        new ProjectListFragment();

        Fragment f = new DashboardFragment();
        fm.beginTransaction().replace(R.id.fragment_container, f, "fragment").commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer!=null && drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(toolbar.getTitle().equals("Dashboard")) {
                super.onBackPressed();
            } else {
                Fragment f = new DashboardFragment();
                fm.beginTransaction().replace(R.id.fragment_container, f, "fragment").commit();
                toolbar.setTitle("Dashboard");
                navigationView.getMenu().getItem(0).setChecked(true);
            }
        }
    }

    @Override
    public void OnProjectDialogClose() {
        dm.saveProjectData();
        if(!toolbar.getTitle().equals("Dashboard")) {
            Fragment f = new ProjectListFragment();
            fm.beginTransaction().replace(R.id.fragment_container, f, "fragment").commit();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Fragment f = null;

        switch(id) {
            case R.id.nav_dashboard:
                f = new DashboardFragment();
                toolbar.setTitle("Dashboard");
                break;
            case R.id.nav_courses:
                f = new CourseListFragment();
                toolbar.setTitle("Courses");
                break;
            case R.id.nav_projects:
                f = new ProjectListFragment();
                toolbar.setTitle("Projects");
                break;
            case R.id.nav_exams:
                Toast.makeText(this, "Not available yet", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_share:
                break;
            case R.id.nav_feedback:
                String url = "http://www.google.com/";
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setData(Uri.parse(url));
                startActivity(browserIntent);
                break;
            case R.id.nav_settings:
                Intent i = new Intent(this, SettingsActivity.class);
                startActivity(i);
                break;
            default: break;
        }

        if(f != null) {
            fm.beginTransaction().replace(R.id.fragment_container, f, "fragment").commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if(drawer!=null)
            drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String courseIdString = data.getStringExtra(getString(R.string.EXTRA_COURSE_UUID));
        dm = DataManager.getInstance(getApplicationContext());
        Course course = dm.getCourse(UUID.fromString(courseIdString));
        dm.updateCourse(course.getCourseId(), course);
        DashboardFragment frag = (DashboardFragment) fm.findFragmentByTag("fragment");
        frag.bindProjectCard();
        frag.bindModuleCard();
    }
}
