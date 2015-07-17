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

import android.database.Cursor;

import com.example.sergio.caminando.ui.DetailFragment;

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
    public int seatsAvailable;
    public String webSafeKey;
    public String organizeName;

    public Attraction() {}

    public Attraction(Long routeId, String name, String description, String topics,
                      String cityNameInit, Long startDate, int maxAttendees, String urlRouteCover,
                      int seatsAvailable, String webSafeKey, String organizeName) {
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

    public Long getRouteId() {
        return routeId;
    }

    public void setRouteId(Long routeId) {
        this.routeId = routeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTopics() {
        return topics;
    }

    public void setTopics(String topics) {
        this.topics = topics;
    }

    public String getCityNameInit() {
        return cityNameInit;
    }

    public void setCityNameInit(String cityNameInit) {
        this.cityNameInit = cityNameInit;
    }

    public Long getStartDate() {
        return startDate;
    }

    public void setStartDate(Long startDate) {
        this.startDate = startDate;
    }

    public int getMaxAttendees() {
        return maxAttendees;
    }

    public void setMaxAttendees(int maxAttendees) {
        this.maxAttendees = maxAttendees;
    }

    public String getUrlRouteCover() {
        return urlRouteCover;
    }

    public void setUrlRouteCover(String urlRouteCover) {
        this.urlRouteCover = urlRouteCover;
    }

    public int getSeatsAvailable() {
        return seatsAvailable;
    }

    public void setSeatsAvailable(int seatsAvailable) {
        this.seatsAvailable = seatsAvailable;
    }

    public String getWebSafeKey() {
        return webSafeKey;
    }

    public void setWebSafeKey(String webSafeKey) {
        this.webSafeKey = webSafeKey;
    }

    public String getOrganizeName() {
        return organizeName;
    }

    public void setOrganizeName(String organizeName) {
        this.organizeName = organizeName;
    }

    public void setRouteDataFromCursor(Cursor cursor) {
        this.routeId = cursor.getLong(DetailFragment.COL_ROUTE_ID);
        this.name = cursor.getString(DetailFragment.COL_NAME_ROUTE);
        this.description = cursor.getString(DetailFragment.COL_DESCRIPTION);
        this.topics = cursor.getString(DetailFragment.COL_TOPICS);
        this.cityNameInit = cursor.getString(DetailFragment.COL_CITY_NAME_INIT);
        this.startDate = cursor.getLong(DetailFragment.COL_START_DATE);
        this.maxAttendees = cursor.getInt(DetailFragment.COL_MAX_ATTENDEES);
        this.urlRouteCover = cursor.getString(DetailFragment.COL_URL_ROUTE_COVER);
        this.seatsAvailable = cursor.getInt(DetailFragment.COL_SEATS_AVAILABLE);
        this.webSafeKey = cursor.getString(DetailFragment.COL_WEBSAFE_KEY);
        this.organizeName = cursor.getString(DetailFragment.COL_ORGANIZER_DISPLAY_NAME);
    }
}