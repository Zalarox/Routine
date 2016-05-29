package com.siddhant.routine.ViewHolders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.siddhant.routine.Classes.Module;
import com.siddhant.routine.Classes.Topic;
import com.siddhant.routine.R;

import java.util.ArrayList;

/**
 * Created by Siddhant on 29-May-16.
 */
public class ModuleCardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    Context context;
    ArrayList<Topic> topicList;

    TextView title;
    TextView totalTopics;
    TextView noTopics;
    ListView moduleTopicList;
    ProgressBar progressBar;
    Button editButton;

    public ModuleCardViewHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;

        title = (TextView) itemView.findViewById(R.id.module_card_title);
        totalTopics = (TextView) itemView.findViewById(R.id.module_card_total_topics);
        noTopics = (TextView) itemView.findViewById(R.id.module_has_no_topics);
        moduleTopicList = (ListView) itemView.findViewById(R.id.module_card_topic_list_view);
        progressBar = (ProgressBar) itemView.findViewById(R.id.module_card_progress_bar);

        editButton = (Button) itemView.findViewById(R.id.module_card_edit_button);
        editButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(!moduleTopicList.isClickable()) {
            editButton.setText("Done");
            moduleTopicList.setClickable(true);
        } else {
            editButton.setText("Edit");
            moduleTopicList.setClickable(false);
        }
    }

    public void bind(Module module) {
        title.setText(context.getString(R.string.module_list_title, getAdapterPosition()+1));
        totalTopics.setText(context.getString(R.string.module_topics, module.getDoneTopics(),
                module.getChildItemList().size()));

        if(module.getChildItemList().isEmpty()) {
            noTopics.setVisibility(View.VISIBLE);
            moduleTopicList.setVisibility(View.GONE);
        } else {
            noTopics.setVisibility(View.GONE);
            moduleTopicList.setVisibility(View.VISIBLE);
        }

        progressBar.setProgress((int) module.getProgress());
        this.topicList = (ArrayList<Topic>) module.getChildItemList();
        ArrayAdapter<Topic> adapter = new ArrayAdapter<Topic>(context,
                android.R.layout.simple_list_item_1, topicList);
        moduleTopicList.setAdapter(adapter);
    }
}
