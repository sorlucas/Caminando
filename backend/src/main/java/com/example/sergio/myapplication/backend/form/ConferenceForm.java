package com.example.sergio.myapplication.backend.form;

import com.google.common.collect.ImmutableList;

import java.util.List;

/**
 * A simple Java object (POJO) representing a Conference form sent from the client.
 */
public class ConferenceForm {
    /**
     * The name of the conference.
     */
    private String name;

    /**
     * The description of the conference.
     */
    private String description;

    /**
     * Topics that are discussed in this conference.
     */
    private List<String> topics;

    /**
     * The city where the conference will take place.
     */
    private String city;

    /**
     * The start date of the conference.
     */
    private Long startDate;

    /**
     * The Url Route Cover
     */
    private String urlPhotoCover;
    /**
     * The capacity of the conference.
     */
    private int maxAttendees;

    private ConferenceForm() {}

    /**
     * Public constructor is solely for Unit Test.
     * @param name
     * @param description
     * @param topics
     * @param city
     * @param startDate
     * @param maxAttendees
     */
    public ConferenceForm(String name, String description, List<String> topics, String city,
                          Long startDate, int maxAttendees) {
        this.name = name;
        this.description = description;
        this.topics = topics == null ? null : ImmutableList.copyOf(topics);
        this.city = city;
        this.startDate = startDate == null ? null : startDate;
        this.maxAttendees = maxAttendees;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getTopics() {
        return topics;
    }

    public String getCity() {
        return city;
    }

    public Long getStartDate() {
        return startDate;
    }

    public String getUrlPhotoCover() {
        return urlPhotoCover;
    }

    public int getMaxAttendees() {
        return maxAttendees;
    }
}