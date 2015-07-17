/*
 * Copyright 2015 Google Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.sergio.caminando.common;

/**
 * A simple shared tourist attraction class to easily pass data around. Used
 * in both the mobile app and wearable app.
 */
public class Attraction {

    public Long routeId;
    public String name;
    public String description;
    public String topics;
    public String cityNameInit;
    public Long startDate;
    public int maxAttendees;
    public String urlRouteCover;
    public String seatsAvailable;
    public String webSafeKey;
    public String organizeName;

    public Attraction() {}

    public Attraction(Long routeId, String name, String description, String topics, String cityNameInit, Long startDate, int maxAttendees, String urlRouteCover, String seatsAvailable, String webSafeKey, String organizeName) {
        this.routeId = routeId;
        this.name = name;
        this.description = description;
        this.topics = topics;
        this.cityNameInit = cityNameInit;
        this.startDate = startDate;
        this.maxAttendees = maxAttendees;
        this.urlRouteCover = urlRouteCover;
        this.seatsAvailable = seatsAvailable;
        this.webSafeKey = webSafeKey;
        this.organizeName = organizeName;
    }


}