package com.siddhant.routine.ViewHolders;

import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.siddhant.routine.R;
import com.siddhant.routine.Classes.Topic;

/**
 * Created by Siddhant on 24-May-16.
 */
public class ModulesChildViewHolder extends ChildViewHolder {

    EditText childTopic;
    CheckBox childTopicDone;

    public ModulesChildViewHolder(View itemView) {
        super(itemView);

        childTopic = (EditText) itemView.findViewById(R.id.expandable_list_group_child_text);
        childTopicDone = (CheckBox) itemView.findViewById
                (R.id.expandable_list_group_child_checkbox);
    }

    public void bind(Topic topic) {
        childTopic.setText(topic.getTopicName());
        childTopicDone.setChecked(topic.isTopicDone());
    }

}
