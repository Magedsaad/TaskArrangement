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

    public Users() {
    }

    public Users(String email, String password, String positionLevel, String age) {
        this.email = email;
        this.password = password;
        this.positionLevel = positionLevel;
        this.age = age;
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
