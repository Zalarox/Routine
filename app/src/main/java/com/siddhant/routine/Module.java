package com.siddhant.routine;


import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Siddhant on 04-Mar-16.
 */
public class Module {
    private int moduleNumber;
    private UUID courseId;
    private int doneTopics;
    private int totalTopics;
    private boolean moduleIsDone;
    private float progress;

    private class Topic {
        int done;
        String name;

        public Topic(String name) {
            this.name = name;
            done = 0;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void toggleDone() {
            if(done == 0) {
                done = 1;
                doneTopics++;

                if(doneTopics == totalTopics) moduleIsDone = true;
                else moduleIsDone = false;
            }
            else {
                done = 0;
                doneTopics--;
            }

            updateValues();
        }
    }

    public ArrayList<Topic> topics;

    public Module(int moduleNumber, UUID courseId) {
        this.moduleNumber = moduleNumber;
        this.courseId = courseId;
    }

    public void addTopic(String name) {
        Topic topic = new Topic(name);
        topics.add(topic);
        totalTopics++;
    }

    public void removeTopic(String name) {
        for(Topic t : topics) {
            if(t.getName().equals(name)) {
                topics.remove(t);
            }
        }
        totalTopics--;
    }

    public ArrayList<Topic> getTopics() {
        return topics;
    }

    public void updateValues() {
        progress = (doneTopics/totalTopics)*100;
    }

    public boolean isModuleDone() {
        return moduleIsDone;
    }

    public float getProgress() {
        return progress;
    }

    public UUID getCourseId() {
        return courseId;
    }

    public void setCourseId(UUID courseId) {
        this.courseId = courseId;
    }

    public int getModuleNumber() {
        return moduleNumber;
    }

    public void setModuleNumber(int moduleNumber) {
        this.moduleNumber = moduleNumber;
    }
}
