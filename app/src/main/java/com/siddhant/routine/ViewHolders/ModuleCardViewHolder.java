package com.siddhant.routine.viewholders;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.siddhant.routine.R;
import com.siddhant.routine.activities.ModuleUpdateActivity;
import com.siddhant.routine.classes.Module;

/**
 * Created by Siddhant on 29-May-16.
 */
public class ModuleCardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    Context context;
    FragmentManager fm;
    Module module;
    TextView title;
    TextView totalTopics;
    View sharedElement;
    ProgressBar progressBar;
    CardView cardView;

    public ModuleCardViewHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;

        cardView = (CardView) itemView.findViewById(R.id.module_card_card_view);
        cardView.setOnClickListener(this);

        sharedElement = itemView.findViewById(R.id.module_card_shared_linear_layout);
        title = (TextView) itemView.findViewById(R.id.module_card_title);
        totalTopics = (TextView) itemView.findViewById(R.id.module_card_total_topics);
        progressBar = (ProgressBar) itemView.findViewById(R.id.module_card_progress_bar);
        fm = ((AppCompatActivity) context).getSupportFragmentManager();
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(context, ModuleUpdateActivity.class);
        i.putExtra(context.getString(R.string.EXTRA_COURSE_UUID), module.getCourseId().toString());
        i.putExtra(context.getString(R.string.EXTRA_MODULE_UUID), module.getModuleId().toString());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {

            Pair<View, String> p1, p2, p3, p4;
            p1 = new Pair<>((View) title, "title");
            p2 = new Pair<>((View) totalTopics, "totalTopics");
            p3 = new Pair<>((View) progressBar, "progressBar");
            p4 = new Pair<>((View) cardView, "test");

            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.
                    makeSceneTransitionAnimation((AppCompatActivity)context, p1, p2, p3, p4);
            ((AppCompatActivity) context).startActivityForResult(i, 0, optionsCompat.toBundle());
        } else {
            ((AppCompatActivity) context).startActivityForResult(i, 0);
        }
    }

    public void bind(Module module) {
        this.module = module;
        title.setText(context.getString(R.string.module_list_title, getAdapterPosition()+1));
        totalTopics.setText(context.getString(R.string.module_topics, module.getDoneTopics(),
                module.getChildItemList().size()));
        progressBar.setProgress((int) Math.floor(module.getProgress()*10000));
    }
}
