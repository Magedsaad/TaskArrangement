package com.trying.developing.taskarrangement.model;

import java.io.Serializable;

/**
 * Created by developing on 4/18/2018.
 */

public class Tasks implements Serializable {
    private String leaderEmail;
    private String userId;
    private String memberEmail;
    private String taskName;
    private String taskDsc;

    public Tasks() {
    }

    public Tasks(String leaderEmail, String userId, String memberEmail, String taskName, String taskDsc) {
        this.leaderEmail = leaderEmail;
        this.userId = userId;
        this.memberEmail = memberEmail;
        this.taskName = taskName;
        this.taskDsc = taskDsc;
    }

    public String getUserId() {
        return userId;
    }

    public String getMemberEmail() {
        return memberEmail;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getTaskDsc() {
        return taskDsc;
    }

    public String getLeaderEmail() {
        return leaderEmail;
    }

    public void setLeaderEmail(String leaderEmail) {
        this.leaderEmail = leaderEmail;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setMemberEmail(String memberEmail) {
        this.memberEmail = memberEmail;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setTaskDsc(String taskDsc) {
        this.taskDsc = taskDsc;
    }
}
