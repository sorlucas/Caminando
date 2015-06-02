package com.example.sergio.myapplication.backend.domain;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import static com.example.sergio.myapplication.backend.form.ProfileForm.TeeShirtSize;


@Entity
public class Profile {

    String displayName;
    String mainEmail;
    TeeShirtSize teeShirtSize;

    @Id
    String userId;

    /**
     * Just making the default constructor private.
     */
    private Profile() {}

    /**
     * Public constructor for Profile.
     * @param userId The user id, obtained from the email
     * @param displayName Any string user wants us to display him/her on this system.
     * @param mainEmail User's main e-mail address.
     * @param teeShirtSize The User's tee shirt size
     *
     */
    public Profile (String userId, String displayName, String mainEmail, TeeShirtSize teeShirtSize) {
        this.userId = userId;
        this.displayName = displayName;
        this.mainEmail = mainEmail;
        this.teeShirtSize = teeShirtSize;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getMainEmail() {
        return mainEmail;
    }

    public TeeShirtSize getTeeShirtSize() {
        return teeShirtSize;
    }

    public String getUserId() {
        return userId;
    }
}