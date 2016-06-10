package com.siddhant.routine.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.siddhant.routine.R;
import com.siddhant.routine.fragments.CourseListFragment;
import com.siddhant.routine.fragments.DashboardFragment;
import com.siddhant.routine.fragments.ProjectEditDialogFragment;
import com.siddhant.routine.fragments.ProjectListFragment;
import com.siddhant.routine.utilities.DataManager;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener,
        ProjectEditDialogFragment.OnProjectDialogCloseListener {

    Toolbar toolbar;
    NavigationView navigationView;
    DataManager dm;
    FragmentManager fm = getSupportFragmentManager();

    private class LoadDataTask extends AsyncTask<Void, Void, Void> {

        public LoadDataTask() {
            dm = DataManager.getInstance(MainActivity.this);
        }

        @Override
        protected Void doInBackground(Void... params) {
            dm.loadCourseData();
            dm.loadProjectData();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(toolbar != null)
            toolbar.setTitle("Dashboard");
        setSupportActionBar(toolbar);
        Fragment f = new DashboardFragment();
        fm.beginTransaction().add(R.id.fragment_container, f).commit();

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

        new LoadDataTask().execute();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer!=null && drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(fm.getBackStackEntryCount() == 0) {
                super.onBackPressed();
            } else {
                fm.popBackStack();
                Fragment f = new DashboardFragment();
                fm.beginTransaction().add(R.id.fragment_container, f).commit();
                toolbar.setTitle("Dashboard");
                navigationView.getMenu().getItem(0).setChecked(true);
            }
        }
    }

    @Override
    public void OnProjectDialogClose() {
        Fragment f = new ProjectListFragment();
        dm.saveProjectData();
        fm.beginTransaction().add(R.id.fragment_container, f).commit();
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
            case R.id.nav_share:

                break;
            case R.id.nav_feedback:

                break;
            case R.id.nav_settings:
                Intent i = new Intent(this, SettingsActivity.class);
                startActivity(i);
                break;
            default: break;
        }

        if(f != null) {
            fm.beginTransaction().add(R.id.fragment_container, f).addToBackStack("tag").commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if(drawer!=null)
            drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
