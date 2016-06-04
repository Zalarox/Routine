package com.siddhant.routine.classes;


import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Siddhant on 04-Mar-16.
 */
public class Module implements Serializable, ParentListItem {

    private UUID moduleId;
    private UUID courseId;
    private boolean moduleIsDone;
    private float progress;
    private ArrayList<Topic> topics;

    public Module(UUID courseId) {
        this.moduleId = UUID.randomUUID();
        this.courseId = courseId;
        topics = new ArrayList<>();
    }

    public void addTopic() {
        Topic topic = new Topic(moduleId);
        topics.add(topic);
    }

    public void removeTopic(int index) {
        topics.remove(index);
    }

    public boolean isModuleDone() {
        return moduleIsDone;
    }

    public float getProgress() {
        if(!topics.isEmpty())
            progress = (getDoneTopics()/(float)topics.size());

        if(progress == 1)
            moduleIsDone = true;
        else
            moduleIsDone = false;

        return progress;
    }

    public int getDoneTopics() {
        int doneTopics=0;
        for(Topic topic : topics) {
            if(topic.isTopicDone())
                doneTopics++;
        }

        if(doneTopics == topics.size())
            moduleIsDone = true;
        else
            moduleIsDone = false;

        return doneTopics;
    }

    public UUID getCourseId() {
        return courseId;
    }

    public UUID getModuleId() {
        return moduleId;
    }

    @Override
    public List<?> getChildItemList() {
        return topics;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }
}
