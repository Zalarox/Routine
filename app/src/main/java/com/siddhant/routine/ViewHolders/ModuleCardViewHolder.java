package com.siddhant.routine.viewholders;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.siddhant.routine.R;
import com.siddhant.routine.activities.MainActivity;
import com.siddhant.routine.activities.ModuleUpdateActivity;
import com.siddhant.routine.classes.Module;
import com.siddhant.routine.utilities.DataManager;

import java.util.ArrayList;

/**
 * Created by Siddhant on 29-May-16.
 */
public class ModuleCardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    Context context;
    Module module;
    TextView title;
    TextView totalTopics;
    ProgressBar progressBar;
    CardView cardView;
    FrameLayout frameLayout;

    DataManager dm;

    public ModuleCardViewHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;
        cardView = (CardView) itemView.findViewById(R.id.module_card_card_view);
        int theme = Integer.parseInt(MainActivity.themeName);
        if(theme > 2) {
            cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.colorDarkerGrey));
        }
        cardView.setOnClickListener(this);
        title = (TextView) itemView.findViewById(R.id.module_card_title);
        totalTopics = (TextView) itemView.findViewById(R.id.module_card_total_topics);
        progressBar = (ProgressBar) itemView.findViewById(R.id.module_card_progress_bar);
        frameLayout = (FrameLayout) itemView.findViewById(R.id.framelayout);
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(context, ModuleUpdateActivity.class);
        i.putExtra(context.getString(R.string.EXTRA_COURSE_UUID), module.getCourseId().toString());
        i.putExtra(context.getString(R.string.EXTRA_MODULE_UUID), module.getModuleId().toString());
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        boolean transitionsEnabled = prefs.getBoolean("transitions", true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && transitionsEnabled) {
            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.
                    makeSceneTransitionAnimation((Activity)context, cardView, "card_view");
            ((Activity) context).startActivityForResult(i, 0, optionsCompat.toBundle());
        } else {
            i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            ((Activity) context).startActivityForResult(i, 0);
        }
    }

    public void bind(Module module) {
        this.module = module;
        dm = DataManager.getInstance(context);
        ArrayList<Module> moduleList = dm.getCourse(module.getCourseId()).getCourseModules();
        int position = moduleList.indexOf(module);

        title.setText(context.getString(R.string.module_list_title, position+1));
        totalTopics.setText(context.getString(R.string.module_topics, module.getDoneTopics(),
                module.getChildItemList().size()));
        progressBar.setProgress((int) Math.floor(module.getProgress()*10000));
    }
}
