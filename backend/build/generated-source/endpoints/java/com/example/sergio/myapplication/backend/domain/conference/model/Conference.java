/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
/*
 * This code was generated by https://code.google.com/p/google-apis-client-generator/
 * (build: 2015-03-26 20:30:19 UTC)
 * on 2015-07-04 at 21:16:12 UTC 
 * Modify at your own risk.
 */

package com.example.sergio.myapplication.backend.domain.conference.model;

/**
 * Model definition for Conference.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the conference. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class Conference extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String city;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String description;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private com.google.api.client.util.DateTime endDate;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long id;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer maxAttendees;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer month;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String name;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String organizerDisplayName;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String photoUrlRouteCover;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer seatsAvailable;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private com.google.api.client.util.DateTime startDate;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.util.List<java.lang.String> topics;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String websafeKey;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getCity() {
    return city;
  }

  /**
   * @param city city or {@code null} for none
   */
  public Conference setCity(java.lang.String city) {
    this.city = city;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getDescription() {
    return description;
  }

  /**
   * @param description description or {@code null} for none
   */
  public Conference setDescription(java.lang.String description) {
    this.description = description;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public com.google.api.client.util.DateTime getEndDate() {
    return endDate;
  }

  /**
   * @param endDate endDate or {@code null} for none
   */
  public Conference setEndDate(com.google.api.client.util.DateTime endDate) {
    this.endDate = endDate;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getId() {
    return id;
  }

  /**
   * @param id id or {@code null} for none
   */
  public Conference setId(java.lang.Long id) {
    this.id = id;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getMaxAttendees() {
    return maxAttendees;
  }

  /**
   * @param maxAttendees maxAttendees or {@code null} for none
   */
  public Conference setMaxAttendees(java.lang.Integer maxAttendees) {
    this.maxAttendees = maxAttendees;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getMonth() {
    return month;
  }

  /**
   * @param month month or {@code null} for none
   */
  public Conference setMonth(java.lang.Integer month) {
    this.month = month;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getName() {
    return name;
  }

  /**
   * @param name name or {@code null} for none
   */
  public Conference setName(java.lang.String name) {
    this.name = name;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getOrganizerDisplayName() {
    return organizerDisplayName;
  }

  /**
   * @param organizerDisplayName organizerDisplayName or {@code null} for none
   */
  public Conference setOrganizerDisplayName(java.lang.String organizerDisplayName) {
    this.organizerDisplayName = organizerDisplayName;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getPhotoUrlRouteCover() {
    return photoUrlRouteCover;
  }

  /**
   * @param photoUrlRouteCover photoUrlRouteCover or {@code null} for none
   */
  public Conference setPhotoUrlRouteCover(java.lang.String photoUrlRouteCover) {
    this.photoUrlRouteCover = photoUrlRouteCover;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getSeatsAvailable() {
    return seatsAvailable;
  }

  /**
   * @param seatsAvailable seatsAvailable or {@code null} for none
   */
  public Conference setSeatsAvailable(java.lang.Integer seatsAvailable) {
    this.seatsAvailable = seatsAvailable;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public com.google.api.client.util.DateTime getStartDate() {
    return startDate;
  }

  /**
   * @param startDate startDate or {@code null} for none
   */
  public Conference setStartDate(com.google.api.client.util.DateTime startDate) {
    this.startDate = startDate;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.util.List<java.lang.String> getTopics() {
    return topics;
  }

  /**
   * @param topics topics or {@code null} for none
   */
  public Conference setTopics(java.util.List<java.lang.String> topics) {
    this.topics = topics;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getWebsafeKey() {
    return websafeKey;
  }

  /**
   * @param websafeKey websafeKey or {@code null} for none
   */
  public Conference setWebsafeKey(java.lang.String websafeKey) {
    this.websafeKey = websafeKey;
    return this;
  }

  @Override
  public Conference set(String fieldName, Object value) {
    return (Conference) super.set(fieldName, value);
  }

  @Override
  public Conference clone() {
    return (Conference) super.clone();
  }

}
