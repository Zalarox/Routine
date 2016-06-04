package com.siddhant.routine.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.siddhant.routine.R;
import com.siddhant.routine.classes.Module;
import com.siddhant.routine.classes.Topic;
import com.siddhant.routine.viewholders.TopicViewHolder;

import java.util.ArrayList;

/**
 * Created by Siddhant on 03-Jun-16.
 */
public class TopicListAdapter extends RecyclerView.Adapter<TopicViewHolder> {

    ArrayList<Topic> topics;
    ProgressBar progressBar;
    TextView totalTopics;
    TextView moduleDonePercent;
    Module module;
    Context context;

    public TopicListAdapter(ArrayList<Topic> topics, ProgressBar progressBar, TextView totalTopics,
                            TextView moduleDonePercent, Module module) {
        this.topics = topics;
        this.progressBar = progressBar;
        this.totalTopics = totalTopics;
        this.module = module;
        this.moduleDonePercent = moduleDonePercent;
    }

    @Override
    public TopicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_topics, parent, false);
        return new TopicViewHolder(view, progressBar, totalTopics, moduleDonePercent, module);
    }

    @Override
    public void onBindViewHolder(TopicViewHolder holder, int position) {
        holder.bind(topics.get(position));
    }

    @Override
    public int getItemCount() {
        return topics.size();
    }
}
