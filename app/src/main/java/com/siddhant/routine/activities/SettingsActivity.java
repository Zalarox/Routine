package com.siddhant.routine.activities;

import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import com.siddhant.routine.R;

public class SettingsActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_fragment);
        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().add(R.id.activity_fragment_container, new SettingsFragment(),
                "settings").commit();
    }

    public static class SettingsFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.app_preferences);
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                getPreferenceScreen().findPreference("transitions").setEnabled(false);
                getPreferenceScreen().findPreference("transitions").
                        setSummary("Only available on Lollipop+ devices.");
            }

            getPreferenceScreen().findPreference("theme").setEnabled(false);
            getPreferenceScreen().findPreference("theme").setSummary("Crash fest, disabled.");
        }
    }

}
