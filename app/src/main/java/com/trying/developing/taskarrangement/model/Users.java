package com.trying.developing.taskarrangement.model;

import java.io.Serializable;

/**
 * Created by developing on 4/18/2018.
 */

public class Users implements Serializable {
    private String email;
    private String password;
    private String positionLevel;
    private String age;
    private String id;

    public Users() {
    }

    public Users(String email, String password, String positionLevel, String age, String id) {
        this.email = email;
        this.password = password;
        this.positionLevel = positionLevel;
        this.age = age;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPositionLevel() {
        return positionLevel;
    }

    public String getAge() {
        return age;
    }
}
