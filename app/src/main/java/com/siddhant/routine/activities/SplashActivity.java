package com.siddhant.routine.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import com.siddhant.routine.R;

public class SplashActivity extends AppCompatActivity {

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
    protected void onCreate(Bundle savedInstanceState) {
//        initTheme();
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
