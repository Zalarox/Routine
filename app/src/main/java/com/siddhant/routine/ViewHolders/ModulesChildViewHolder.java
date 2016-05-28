package com.siddhant.routine.ViewHolders;

import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.siddhant.routine.Adapters.ModuleExpandableListAdapter;
import com.siddhant.routine.Classes.Module;
import com.siddhant.routine.Classes.Topic;
import com.siddhant.routine.R;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Siddhant on 24-May-16.
 */
public class ModulesChildViewHolder extends ChildViewHolder {

    Topic topic;
    ArrayList<Module> moduleList;
    ArrayList<Topic> topicList;
    UUID moduleId;
    EditText childTopic;
    CheckBox childTopicDone;

    public ModulesChildViewHolder(final ModuleExpandableListAdapter adapter, View itemView) {
        super(itemView);

        childTopic = (EditText) itemView.findViewById(R.id.expandable_list_group_child_text);
        childTopicDone = (CheckBox) itemView.findViewById
                (R.id.expandable_list_group_child_checkbox);

        childTopic.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                moduleList = (ArrayList<Module>) adapter.getParentItemList();
                for(Module module : moduleList) {
                    if(module.getModuleId().equals(moduleId)) {
                        topicList = (ArrayList<Topic>) module.getChildItemList();
                    }
                }

                topic.setTopicName(childTopic.getText().toString());

                if(!hasFocus && topicList.indexOf(topic) != topicList.size()-1) {
                    if(TextUtils.isEmpty(childTopic.getText())) {
                        adapter.removeTopicChild(moduleId, topicList.indexOf(topic));
                    }
                }

                if(hasFocus && topicList.indexOf(topic) == topicList.size()-1) {
                    adapter.addTopicChild(moduleId, topicList.size());
                }
            }
        });

        childTopicDone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                topic.setTopicDone(isChecked);
            }
        });
    }

    public void bind(Topic topic) {
        this.topic = topic;
        moduleId = topic.getModuleId();
        childTopic.setText(topic.getTopicName());
        childTopicDone.setChecked(topic.isTopicDone());
    }

}
