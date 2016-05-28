package com.siddhant.routine.Classes;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by Siddhant on 24-May-16.
 */
public class Topic implements Serializable {
    private UUID moduleId;
    private String topicName;
    private boolean isTopicDone;

    public Topic(UUID moduleId) {
        this.moduleId = moduleId;
        this.topicName = "";
    }

    public String getTopicName() {
        return topicName;
    }

    public boolean isTopicDone() {
        return isTopicDone;
    }

    public UUID getModuleId() { return moduleId; }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public void setTopicDone(boolean isTopicDone) {
        this.isTopicDone = isTopicDone;
    }

}
