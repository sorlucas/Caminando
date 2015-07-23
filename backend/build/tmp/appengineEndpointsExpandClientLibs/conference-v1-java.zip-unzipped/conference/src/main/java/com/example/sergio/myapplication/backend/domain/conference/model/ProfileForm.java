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
 * (build: 2015-07-16 18:28:29 UTC)
 * on 2015-07-23 at 15:59:35 UTC 
 * Modify at your own risk.
 */

package com.example.sergio.myapplication.backend.domain.conference.model;

/**
 * Model definition for ProfileForm.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the conference. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class ProfileForm extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String displayName;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String teeShirtSize;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getDisplayName() {
    return displayName;
  }

  /**
   * @param displayName displayName or {@code null} for none
   */
  public ProfileForm setDisplayName(java.lang.String displayName) {
    this.displayName = displayName;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getTeeShirtSize() {
    return teeShirtSize;
  }

  /**
   * @param teeShirtSize teeShirtSize or {@code null} for none
   */
  public ProfileForm setTeeShirtSize(java.lang.String teeShirtSize) {
    this.teeShirtSize = teeShirtSize;
    return this;
  }

  @Override
  public ProfileForm set(String fieldName, Object value) {
    return (ProfileForm) super.set(fieldName, value);
  }

  @Override
  public ProfileForm clone() {
    return (ProfileForm) super.clone();
  }

}
