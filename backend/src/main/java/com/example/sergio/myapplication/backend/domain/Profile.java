package com.example.sergio.myapplication.backend.domain;

import com.google.common.collect.ImmutableList;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import java.util.ArrayList;
import java.util.List;

import static com.example.sergio.myapplication.backend.form.ProfileForm.TeeShirtSize;


@Entity
public class Profile {

    String displayName;
    String mainEmail;
    TeeShirtSize teeShirtSize;

    @Id
    String userId;

    private List<String> conferenceKeysToAttend = new ArrayList<>(0);

    public List<String> getConferenceKeysToAttend() {
        return ImmutableList.copyOf(conferenceKeysToAttend);
    }

    public void addToConferenceKeysToAttend(String conferenceKey){
        conferenceKeysToAttend.add(conferenceKey);
    }

    /**
     * Remove the conferenceId from conferenceIdsToAttend.
     *
     * @param conferenceKey a websafe String representation of the Conference Key.
     */
    public void unregisterFromConference(String conferenceKey){
        if (conferenceKeysToAttend.contains(conferenceKey)){
            conferenceKeysToAttend.remove(conferenceKey);
        } else {
            throw new IllegalArgumentException("Invalid conferenceKey: " + conferenceKey);
        }
    }

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

    /**
     *  Update the Profile with the given displayName and teeShirtSize
     *
     *  @param displayName Any string user wants us to display him/her on this system.
     *  @param teeShirtSize The User's tee shirt size
     */
    public  void update(String displayName, TeeShirtSize teeShirtSize){
        if (displayName != null){
            this.displayName = displayName;
        }
        if (teeShirtSize != null){
            this.teeShirtSize = teeShirtSize;
        }
    }
}