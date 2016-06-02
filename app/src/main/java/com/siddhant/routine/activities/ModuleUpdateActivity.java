package com.siddhant.routine.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.transition.TransitionInflater;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.siddhant.routine.R;

/**
 * Created by Siddhant on 01-Jun-16.
 */
public class ModuleUpdateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setSharedElementEnterTransition(TransitionInflater.from(this).
                    inflateTransition(R.transition.shared_element_transition));
        }

        setContentView(R.layout.activity_module_update);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);


        TextView moduleTitle = (TextView) findViewById(R.id.module_card_title);
        TextView totalTopics = (TextView) findViewById(R.id.module_card_total_topics);
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.module_card_progress_bar);
        ListView moduleTopicList = (ListView) findViewById(R.id.dialog_module_list);

    }
}
