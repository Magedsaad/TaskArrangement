package com.trying.developing.taskarrangement.model;

/**
 * Created by developing on 5/1/2018.
 */

public class Chat {

    private String message;
    private String friend_id;

    public Chat() {
    }

    public Chat(String message, String friend_id) {
        this.message = message;
        this.friend_id = friend_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFriend_id() {
        return friend_id;
    }

    public void setFriend_id(String friend_id) {
        this.friend_id = friend_id;
    }
}
