package com.siddhant.routine.Activities;

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

import com.siddhant.routine.Utilities.CourseManager;
import com.siddhant.routine.R;
import com.siddhant.routine.Fragments.CourseListFragment;
import com.siddhant.routine.Fragments.DashboardFragment;
import com.siddhant.routine.Fragments.ProjectListFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    NavigationView navigationView;
    CourseManager cm;
    FragmentManager fm = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cm = CourseManager.getInstance(getApplicationContext());

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Dashboard");

        Fragment f = new DashboardFragment();
        fm.beginTransaction().add(R.id.fragment_container, f).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        cm.loadData();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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
            case R.id.nav_share:

                break;
            case R.id.nav_feedback:

                break;
            default: break;
        }

        if(f != null) {
            fm.beginTransaction().add(R.id.fragment_container, f).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
