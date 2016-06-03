package com.siddhant.routine.viewholders;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.siddhant.routine.R;
import com.siddhant.routine.classes.Module;
import com.siddhant.routine.classes.Topic;

/**
 * Created by Siddhant on 03-Jun-16.
 */
public class TopicViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
        View.OnLongClickListener {

    Topic topic;
    Module module;
    TextView topicTitle;
    ProgressBar progressBar;
    TextView totalTopics;
    Context context;
    int colorDone, colorNotDone;

    public TopicViewHolder(View itemView, ProgressBar progressBar, TextView totalTopics,
                           Module module) {
        super(itemView);
        context = itemView.getContext();
        topicTitle = (TextView) itemView.findViewById(R.id.list_item_topic_title);
        this.progressBar = progressBar;
        this.module = module;
        this.totalTopics = totalTopics;
        colorNotDone = ContextCompat.getColor(context, R.color.colorBackground);
        colorDone = ContextCompat.getColor(context, R.color.colorTopicDone);

        progressBar.setProgress((int) Math.floor(module.getProgress()*10000));

        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    public void bind(Topic topic) {
        this.topic = topic;
        topicTitle.setText(topic.getTopicName());
        if(topic.isTopicDone()) {
            topicTitle.setTextColor(colorDone);
        } else {
            topicTitle.setTextColor(colorNotDone);
        }
    }

    @Override
    public void onClick(View v) {
        if(topic.toggleDone()) {
            topicTitle.setTextColor(colorDone);
        } else {
            topicTitle.setTextColor(colorNotDone);
        }

        ObjectAnimator animation = ObjectAnimator.ofInt(progressBar, "progress",
                (int) Math.floor(module.getProgress()*10000));
        animation.setDuration(500);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();

        totalTopics.setText(context.getString(R.string.module_topics, module.getDoneTopics(),
                module.getChildItemList().size()));
    }

    @Override
    public boolean onLongClick(View v) {
        Toast.makeText(context, topic.getTopicName(), Toast.LENGTH_SHORT).show();
        return true;
    }
}
