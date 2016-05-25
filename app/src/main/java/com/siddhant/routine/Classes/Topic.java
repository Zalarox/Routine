package com.siddhant.routine.Classes;

import java.util.UUID;

/**
 * Created by Siddhant on 24-May-16.
 */
public class Topic {
    private int topicId;
    private UUID moduleId;
    private String topicName;
    private boolean isTopicDone;

    public Topic(UUID moduleId, String topicName) {
        this.moduleId = moduleId;
        this.topicName = topicName;
    }

    public Topic(UUID moduleId, String topicName, boolean isTopicDone) {
        this.moduleId = moduleId;
        this.topicName = topicName;
        this.isTopicDone = isTopicDone;
    }

    public String getTopicName() {
        return topicName;
    }

    public boolean isTopicDone() {
        return isTopicDone;
    }

}
