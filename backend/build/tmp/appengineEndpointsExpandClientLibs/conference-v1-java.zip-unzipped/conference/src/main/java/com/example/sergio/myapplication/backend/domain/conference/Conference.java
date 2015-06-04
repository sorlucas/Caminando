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
 * on 2015-06-04 at 19:11:17 UTC 
 * Modify at your own risk.
 */

package com.example.sergio.myapplication.backend.domain.conference;

/**
 * Service definition for Conference (v1).
 *
 * <p>
 * Conference Central API for creating and querying conferences, and for creating and getting user Profiles
 * </p>
 *
 * <p>
 * For more information about this service, see the
 * <a href="" target="_blank">API Documentation</a>
 * </p>
 *
 * <p>
 * This service uses {@link ConferenceRequestInitializer} to initialize global parameters via its
 * {@link Builder}.
 * </p>
 *
 * @since 1.3
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public class Conference extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient {

  // Note: Leave this static initializer at the top of the file.
  static {
    com.google.api.client.util.Preconditions.checkState(
        com.google.api.client.googleapis.GoogleUtils.MAJOR_VERSION == 1 &&
        com.google.api.client.googleapis.GoogleUtils.MINOR_VERSION >= 15,
        "You are currently running with version %s of google-api-client. " +
        "You need at least version 1.15 of google-api-client to run version " +
        "1.20.0 of the conference library.", com.google.api.client.googleapis.GoogleUtils.VERSION);
  }

  /**
   * The default encoded root URL of the service. This is determined when the library is generated
   * and normally should not be changed.
   *
   * @since 1.7
   */
  public static final String DEFAULT_ROOT_URL = "https://rutas-senderistas.appspot.com/_ah/api/";

  /**
   * The default encoded service path of the service. This is determined when the library is
   * generated and normally should not be changed.
   *
   * @since 1.7
   */
  public static final String DEFAULT_SERVICE_PATH = "conference/v1/";

  /**
   * The default encoded base URL of the service. This is determined when the library is generated
   * and normally should not be changed.
   */
  public static final String DEFAULT_BASE_URL = DEFAULT_ROOT_URL + DEFAULT_SERVICE_PATH;

  /**
   * Constructor.
   *
   * <p>
   * Use {@link Builder} if you need to specify any of the optional parameters.
   * </p>
   *
   * @param transport HTTP transport, which should normally be:
   *        <ul>
   *        <li>Google App Engine:
   *        {@code com.google.api.client.extensions.appengine.http.UrlFetchTransport}</li>
   *        <li>Android: {@code newCompatibleTransport} from
   *        {@code com.google.api.client.extensions.android.http.AndroidHttp}</li>
   *        <li>Java: {@link com.google.api.client.googleapis.javanet.GoogleNetHttpTransport#newTrustedTransport()}
   *        </li>
   *        </ul>
   * @param jsonFactory JSON factory, which may be:
   *        <ul>
   *        <li>Jackson: {@code com.google.api.client.json.jackson2.JacksonFactory}</li>
   *        <li>Google GSON: {@code com.google.api.client.json.gson.GsonFactory}</li>
   *        <li>Android Honeycomb or higher:
   *        {@code com.google.api.client.extensions.android.json.AndroidJsonFactory}</li>
   *        </ul>
   * @param httpRequestInitializer HTTP request initializer or {@code null} for none
   * @since 1.7
   */
  public Conference(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
      com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
    this(new Builder(transport, jsonFactory, httpRequestInitializer));
  }

  /**
   * @param builder builder
   */
  Conference(Builder builder) {
    super(builder);
  }

  @Override
  protected void initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest<?> httpClientRequest) throws java.io.IOException {
    super.initialize(httpClientRequest);
  }

  /**
   * Create a request for the method "createConference".
   *
   * This request holds the parameters needed by the conference server.  After setting any optional
   * parameters, call the {@link CreateConference#execute()} method to invoke the remote operation.
   *
   * @param content the {@link com.example.sergio.myapplication.backend.domain.conference.model.ConferenceForm}
   * @return the request
   */
  public CreateConference createConference(com.example.sergio.myapplication.backend.domain.conference.model.ConferenceForm content) throws java.io.IOException {
    CreateConference result = new CreateConference(content);
    initialize(result);
    return result;
  }

  public class CreateConference extends ConferenceRequest<com.example.sergio.myapplication.backend.domain.conference.model.Conference> {

    private static final String REST_PATH = "conference";

    /**
     * Create a request for the method "createConference".
     *
     * This request holds the parameters needed by the the conference server.  After setting any
     * optional parameters, call the {@link CreateConference#execute()} method to invoke the remote
     * operation. <p> {@link CreateConference#initialize(com.google.api.client.googleapis.services.Abs
     * tractGoogleClientRequest)} must be called to initialize this instance immediately after
     * invoking the constructor. </p>
     *
     * @param content the {@link com.example.sergio.myapplication.backend.domain.conference.model.ConferenceForm}
     * @since 1.13
     */
    protected CreateConference(com.example.sergio.myapplication.backend.domain.conference.model.ConferenceForm content) {
      super(Conference.this, "POST", REST_PATH, content, com.example.sergio.myapplication.backend.domain.conference.model.Conference.class);
    }

    @Override
    public CreateConference setAlt(java.lang.String alt) {
      return (CreateConference) super.setAlt(alt);
    }

    @Override
    public CreateConference setFields(java.lang.String fields) {
      return (CreateConference) super.setFields(fields);
    }

    @Override
    public CreateConference setKey(java.lang.String key) {
      return (CreateConference) super.setKey(key);
    }

    @Override
    public CreateConference setOauthToken(java.lang.String oauthToken) {
      return (CreateConference) super.setOauthToken(oauthToken);
    }

    @Override
    public CreateConference setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (CreateConference) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public CreateConference setQuotaUser(java.lang.String quotaUser) {
      return (CreateConference) super.setQuotaUser(quotaUser);
    }

    @Override
    public CreateConference setUserIp(java.lang.String userIp) {
      return (CreateConference) super.setUserIp(userIp);
    }

    @Override
    public CreateConference set(String parameterName, Object value) {
      return (CreateConference) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "getAnnouncement".
   *
   * This request holds the parameters needed by the conference server.  After setting any optional
   * parameters, call the {@link GetAnnouncement#execute()} method to invoke the remote operation.
   *
   * @return the request
   */
  public GetAnnouncement getAnnouncement() throws java.io.IOException {
    GetAnnouncement result = new GetAnnouncement();
    initialize(result);
    return result;
  }

  public class GetAnnouncement extends ConferenceRequest<com.example.sergio.myapplication.backend.domain.conference.model.Announcement> {

    private static final String REST_PATH = "announcement";

    /**
     * Create a request for the method "getAnnouncement".
     *
     * This request holds the parameters needed by the the conference server.  After setting any
     * optional parameters, call the {@link GetAnnouncement#execute()} method to invoke the remote
     * operation. <p> {@link GetAnnouncement#initialize(com.google.api.client.googleapis.services.Abst
     * ractGoogleClientRequest)} must be called to initialize this instance immediately after invoking
     * the constructor. </p>
     *
     * @since 1.13
     */
    protected GetAnnouncement() {
      super(Conference.this, "GET", REST_PATH, null, com.example.sergio.myapplication.backend.domain.conference.model.Announcement.class);
    }

    @Override
    public com.google.api.client.http.HttpResponse executeUsingHead() throws java.io.IOException {
      return super.executeUsingHead();
    }

    @Override
    public com.google.api.client.http.HttpRequest buildHttpRequestUsingHead() throws java.io.IOException {
      return super.buildHttpRequestUsingHead();
    }

    @Override
    public GetAnnouncement setAlt(java.lang.String alt) {
      return (GetAnnouncement) super.setAlt(alt);
    }

    @Override
    public GetAnnouncement setFields(java.lang.String fields) {
      return (GetAnnouncement) super.setFields(fields);
    }

    @Override
    public GetAnnouncement setKey(java.lang.String key) {
      return (GetAnnouncement) super.setKey(key);
    }

    @Override
    public GetAnnouncement setOauthToken(java.lang.String oauthToken) {
      return (GetAnnouncement) super.setOauthToken(oauthToken);
    }

    @Override
    public GetAnnouncement setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (GetAnnouncement) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public GetAnnouncement setQuotaUser(java.lang.String quotaUser) {
      return (GetAnnouncement) super.setQuotaUser(quotaUser);
    }

    @Override
    public GetAnnouncement setUserIp(java.lang.String userIp) {
      return (GetAnnouncement) super.setUserIp(userIp);
    }

    @Override
    public GetAnnouncement set(String parameterName, Object value) {
      return (GetAnnouncement) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "getConference".
   *
   * This request holds the parameters needed by the conference server.  After setting any optional
   * parameters, call the {@link GetConference#execute()} method to invoke the remote operation.
   *
   * @param websafeConferenceKey
   * @return the request
   */
  public GetConference getConference(java.lang.String websafeConferenceKey) throws java.io.IOException {
    GetConference result = new GetConference(websafeConferenceKey);
    initialize(result);
    return result;
  }

  public class GetConference extends ConferenceRequest<com.example.sergio.myapplication.backend.domain.conference.model.Conference> {

    private static final String REST_PATH = "conference/{websafeConferenceKey}";

    /**
     * Create a request for the method "getConference".
     *
     * This request holds the parameters needed by the the conference server.  After setting any
     * optional parameters, call the {@link GetConference#execute()} method to invoke the remote
     * operation. <p> {@link GetConference#initialize(com.google.api.client.googleapis.services.Abstra
     * ctGoogleClientRequest)} must be called to initialize this instance immediately after invoking
     * the constructor. </p>
     *
     * @param websafeConferenceKey
     * @since 1.13
     */
    protected GetConference(java.lang.String websafeConferenceKey) {
      super(Conference.this, "GET", REST_PATH, null, com.example.sergio.myapplication.backend.domain.conference.model.Conference.class);
      this.websafeConferenceKey = com.google.api.client.util.Preconditions.checkNotNull(websafeConferenceKey, "Required parameter websafeConferenceKey must be specified.");
    }

    @Override
    public com.google.api.client.http.HttpResponse executeUsingHead() throws java.io.IOException {
      return super.executeUsingHead();
    }

    @Override
    public com.google.api.client.http.HttpRequest buildHttpRequestUsingHead() throws java.io.IOException {
      return super.buildHttpRequestUsingHead();
    }

    @Override
    public GetConference setAlt(java.lang.String alt) {
      return (GetConference) super.setAlt(alt);
    }

    @Override
    public GetConference setFields(java.lang.String fields) {
      return (GetConference) super.setFields(fields);
    }

    @Override
    public GetConference setKey(java.lang.String key) {
      return (GetConference) super.setKey(key);
    }

    @Override
    public GetConference setOauthToken(java.lang.String oauthToken) {
      return (GetConference) super.setOauthToken(oauthToken);
    }

    @Override
    public GetConference setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (GetConference) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public GetConference setQuotaUser(java.lang.String quotaUser) {
      return (GetConference) super.setQuotaUser(quotaUser);
    }

    @Override
    public GetConference setUserIp(java.lang.String userIp) {
      return (GetConference) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String websafeConferenceKey;

    /**

     */
    public java.lang.String getWebsafeConferenceKey() {
      return websafeConferenceKey;
    }

    public GetConference setWebsafeConferenceKey(java.lang.String websafeConferenceKey) {
      this.websafeConferenceKey = websafeConferenceKey;
      return this;
    }

    @Override
    public GetConference set(String parameterName, Object value) {
      return (GetConference) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "getConferencesCreated".
   *
   * This request holds the parameters needed by the conference server.  After setting any optional
   * parameters, call the {@link GetConferencesCreated#execute()} method to invoke the remote
   * operation.
   *
   * @return the request
   */
  public GetConferencesCreated getConferencesCreated() throws java.io.IOException {
    GetConferencesCreated result = new GetConferencesCreated();
    initialize(result);
    return result;
  }

  public class GetConferencesCreated extends ConferenceRequest<com.example.sergio.myapplication.backend.domain.conference.model.ConferenceCollection> {

    private static final String REST_PATH = "getConferencesCreated";

    /**
     * Create a request for the method "getConferencesCreated".
     *
     * This request holds the parameters needed by the the conference server.  After setting any
     * optional parameters, call the {@link GetConferencesCreated#execute()} method to invoke the
     * remote operation. <p> {@link GetConferencesCreated#initialize(com.google.api.client.googleapis.
     * services.AbstractGoogleClientRequest)} must be called to initialize this instance immediately
     * after invoking the constructor. </p>
     *
     * @since 1.13
     */
    protected GetConferencesCreated() {
      super(Conference.this, "POST", REST_PATH, null, com.example.sergio.myapplication.backend.domain.conference.model.ConferenceCollection.class);
    }

    @Override
    public GetConferencesCreated setAlt(java.lang.String alt) {
      return (GetConferencesCreated) super.setAlt(alt);
    }

    @Override
    public GetConferencesCreated setFields(java.lang.String fields) {
      return (GetConferencesCreated) super.setFields(fields);
    }

    @Override
    public GetConferencesCreated setKey(java.lang.String key) {
      return (GetConferencesCreated) super.setKey(key);
    }

    @Override
    public GetConferencesCreated setOauthToken(java.lang.String oauthToken) {
      return (GetConferencesCreated) super.setOauthToken(oauthToken);
    }

    @Override
    public GetConferencesCreated setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (GetConferencesCreated) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public GetConferencesCreated setQuotaUser(java.lang.String quotaUser) {
      return (GetConferencesCreated) super.setQuotaUser(quotaUser);
    }

    @Override
    public GetConferencesCreated setUserIp(java.lang.String userIp) {
      return (GetConferencesCreated) super.setUserIp(userIp);
    }

    @Override
    public GetConferencesCreated set(String parameterName, Object value) {
      return (GetConferencesCreated) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "getConferencesToAttend".
   *
   * This request holds the parameters needed by the conference server.  After setting any optional
   * parameters, call the {@link GetConferencesToAttend#execute()} method to invoke the remote
   * operation.
   *
   * @return the request
   */
  public GetConferencesToAttend getConferencesToAttend() throws java.io.IOException {
    GetConferencesToAttend result = new GetConferencesToAttend();
    initialize(result);
    return result;
  }

  public class GetConferencesToAttend extends ConferenceRequest<com.example.sergio.myapplication.backend.domain.conference.model.ConferenceCollection> {

    private static final String REST_PATH = "getConferencesToAttend";

    /**
     * Create a request for the method "getConferencesToAttend".
     *
     * This request holds the parameters needed by the the conference server.  After setting any
     * optional parameters, call the {@link GetConferencesToAttend#execute()} method to invoke the
     * remote operation. <p> {@link GetConferencesToAttend#initialize(com.google.api.client.googleapis
     * .services.AbstractGoogleClientRequest)} must be called to initialize this instance immediately
     * after invoking the constructor. </p>
     *
     * @since 1.13
     */
    protected GetConferencesToAttend() {
      super(Conference.this, "GET", REST_PATH, null, com.example.sergio.myapplication.backend.domain.conference.model.ConferenceCollection.class);
    }

    @Override
    public com.google.api.client.http.HttpResponse executeUsingHead() throws java.io.IOException {
      return super.executeUsingHead();
    }

    @Override
    public com.google.api.client.http.HttpRequest buildHttpRequestUsingHead() throws java.io.IOException {
      return super.buildHttpRequestUsingHead();
    }

    @Override
    public GetConferencesToAttend setAlt(java.lang.String alt) {
      return (GetConferencesToAttend) super.setAlt(alt);
    }

    @Override
    public GetConferencesToAttend setFields(java.lang.String fields) {
      return (GetConferencesToAttend) super.setFields(fields);
    }

    @Override
    public GetConferencesToAttend setKey(java.lang.String key) {
      return (GetConferencesToAttend) super.setKey(key);
    }

    @Override
    public GetConferencesToAttend setOauthToken(java.lang.String oauthToken) {
      return (GetConferencesToAttend) super.setOauthToken(oauthToken);
    }

    @Override
    public GetConferencesToAttend setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (GetConferencesToAttend) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public GetConferencesToAttend setQuotaUser(java.lang.String quotaUser) {
      return (GetConferencesToAttend) super.setQuotaUser(quotaUser);
    }

    @Override
    public GetConferencesToAttend setUserIp(java.lang.String userIp) {
      return (GetConferencesToAttend) super.setUserIp(userIp);
    }

    @Override
    public GetConferencesToAttend set(String parameterName, Object value) {
      return (GetConferencesToAttend) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "getProfile".
   *
   * This request holds the parameters needed by the conference server.  After setting any optional
   * parameters, call the {@link GetProfile#execute()} method to invoke the remote operation.
   *
   * @return the request
   */
  public GetProfile getProfile() throws java.io.IOException {
    GetProfile result = new GetProfile();
    initialize(result);
    return result;
  }

  public class GetProfile extends ConferenceRequest<com.example.sergio.myapplication.backend.domain.conference.model.Profile> {

    private static final String REST_PATH = "profile";

    /**
     * Create a request for the method "getProfile".
     *
     * This request holds the parameters needed by the the conference server.  After setting any
     * optional parameters, call the {@link GetProfile#execute()} method to invoke the remote
     * operation. <p> {@link
     * GetProfile#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @since 1.13
     */
    protected GetProfile() {
      super(Conference.this, "GET", REST_PATH, null, com.example.sergio.myapplication.backend.domain.conference.model.Profile.class);
    }

    @Override
    public com.google.api.client.http.HttpResponse executeUsingHead() throws java.io.IOException {
      return super.executeUsingHead();
    }

    @Override
    public com.google.api.client.http.HttpRequest buildHttpRequestUsingHead() throws java.io.IOException {
      return super.buildHttpRequestUsingHead();
    }

    @Override
    public GetProfile setAlt(java.lang.String alt) {
      return (GetProfile) super.setAlt(alt);
    }

    @Override
    public GetProfile setFields(java.lang.String fields) {
      return (GetProfile) super.setFields(fields);
    }

    @Override
    public GetProfile setKey(java.lang.String key) {
      return (GetProfile) super.setKey(key);
    }

    @Override
    public GetProfile setOauthToken(java.lang.String oauthToken) {
      return (GetProfile) super.setOauthToken(oauthToken);
    }

    @Override
    public GetProfile setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (GetProfile) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public GetProfile setQuotaUser(java.lang.String quotaUser) {
      return (GetProfile) super.setQuotaUser(quotaUser);
    }

    @Override
    public GetProfile setUserIp(java.lang.String userIp) {
      return (GetProfile) super.setUserIp(userIp);
    }

    @Override
    public GetProfile set(String parameterName, Object value) {
      return (GetProfile) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "queryConferences".
   *
   * This request holds the parameters needed by the conference server.  After setting any optional
   * parameters, call the {@link QueryConferences#execute()} method to invoke the remote operation.
   *
   * @param content the {@link com.example.sergio.myapplication.backend.domain.conference.model.ConferenceQueryForm}
   * @return the request
   */
  public QueryConferences queryConferences(com.example.sergio.myapplication.backend.domain.conference.model.ConferenceQueryForm content) throws java.io.IOException {
    QueryConferences result = new QueryConferences(content);
    initialize(result);
    return result;
  }

  public class QueryConferences extends ConferenceRequest<com.example.sergio.myapplication.backend.domain.conference.model.ConferenceCollection> {

    private static final String REST_PATH = "queryConferences";

    /**
     * Create a request for the method "queryConferences".
     *
     * This request holds the parameters needed by the the conference server.  After setting any
     * optional parameters, call the {@link QueryConferences#execute()} method to invoke the remote
     * operation. <p> {@link QueryConferences#initialize(com.google.api.client.googleapis.services.Abs
     * tractGoogleClientRequest)} must be called to initialize this instance immediately after
     * invoking the constructor. </p>
     *
     * @param content the {@link com.example.sergio.myapplication.backend.domain.conference.model.ConferenceQueryForm}
     * @since 1.13
     */
    protected QueryConferences(com.example.sergio.myapplication.backend.domain.conference.model.ConferenceQueryForm content) {
      super(Conference.this, "POST", REST_PATH, content, com.example.sergio.myapplication.backend.domain.conference.model.ConferenceCollection.class);
    }

    @Override
    public QueryConferences setAlt(java.lang.String alt) {
      return (QueryConferences) super.setAlt(alt);
    }

    @Override
    public QueryConferences setFields(java.lang.String fields) {
      return (QueryConferences) super.setFields(fields);
    }

    @Override
    public QueryConferences setKey(java.lang.String key) {
      return (QueryConferences) super.setKey(key);
    }

    @Override
    public QueryConferences setOauthToken(java.lang.String oauthToken) {
      return (QueryConferences) super.setOauthToken(oauthToken);
    }

    @Override
    public QueryConferences setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (QueryConferences) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public QueryConferences setQuotaUser(java.lang.String quotaUser) {
      return (QueryConferences) super.setQuotaUser(quotaUser);
    }

    @Override
    public QueryConferences setUserIp(java.lang.String userIp) {
      return (QueryConferences) super.setUserIp(userIp);
    }

    @Override
    public QueryConferences set(String parameterName, Object value) {
      return (QueryConferences) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "registerForConference".
   *
   * This request holds the parameters needed by the conference server.  After setting any optional
   * parameters, call the {@link RegisterForConference#execute()} method to invoke the remote
   * operation.
   *
   * @param websafeConferenceKey
   * @return the request
   */
  public RegisterForConference registerForConference(java.lang.String websafeConferenceKey) throws java.io.IOException {
    RegisterForConference result = new RegisterForConference(websafeConferenceKey);
    initialize(result);
    return result;
  }

  public class RegisterForConference extends ConferenceRequest<com.example.sergio.myapplication.backend.domain.conference.model.WrappedBoolean> {

    private static final String REST_PATH = "registerForConference/{websafeConferenceKey}/registration";

    /**
     * Create a request for the method "registerForConference".
     *
     * This request holds the parameters needed by the the conference server.  After setting any
     * optional parameters, call the {@link RegisterForConference#execute()} method to invoke the
     * remote operation. <p> {@link RegisterForConference#initialize(com.google.api.client.googleapis.
     * services.AbstractGoogleClientRequest)} must be called to initialize this instance immediately
     * after invoking the constructor. </p>
     *
     * @param websafeConferenceKey
     * @since 1.13
     */
    protected RegisterForConference(java.lang.String websafeConferenceKey) {
      super(Conference.this, "POST", REST_PATH, null, com.example.sergio.myapplication.backend.domain.conference.model.WrappedBoolean.class);
      this.websafeConferenceKey = com.google.api.client.util.Preconditions.checkNotNull(websafeConferenceKey, "Required parameter websafeConferenceKey must be specified.");
    }

    @Override
    public RegisterForConference setAlt(java.lang.String alt) {
      return (RegisterForConference) super.setAlt(alt);
    }

    @Override
    public RegisterForConference setFields(java.lang.String fields) {
      return (RegisterForConference) super.setFields(fields);
    }

    @Override
    public RegisterForConference setKey(java.lang.String key) {
      return (RegisterForConference) super.setKey(key);
    }

    @Override
    public RegisterForConference setOauthToken(java.lang.String oauthToken) {
      return (RegisterForConference) super.setOauthToken(oauthToken);
    }

    @Override
    public RegisterForConference setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (RegisterForConference) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public RegisterForConference setQuotaUser(java.lang.String quotaUser) {
      return (RegisterForConference) super.setQuotaUser(quotaUser);
    }

    @Override
    public RegisterForConference setUserIp(java.lang.String userIp) {
      return (RegisterForConference) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String websafeConferenceKey;

    /**

     */
    public java.lang.String getWebsafeConferenceKey() {
      return websafeConferenceKey;
    }

    public RegisterForConference setWebsafeConferenceKey(java.lang.String websafeConferenceKey) {
      this.websafeConferenceKey = websafeConferenceKey;
      return this;
    }

    @Override
    public RegisterForConference set(String parameterName, Object value) {
      return (RegisterForConference) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "saveProfile".
   *
   * This request holds the parameters needed by the conference server.  After setting any optional
   * parameters, call the {@link SaveProfile#execute()} method to invoke the remote operation.
   *
   * @param content the {@link com.example.sergio.myapplication.backend.domain.conference.model.ProfileForm}
   * @return the request
   */
  public SaveProfile saveProfile(com.example.sergio.myapplication.backend.domain.conference.model.ProfileForm content) throws java.io.IOException {
    SaveProfile result = new SaveProfile(content);
    initialize(result);
    return result;
  }

  public class SaveProfile extends ConferenceRequest<com.example.sergio.myapplication.backend.domain.conference.model.Profile> {

    private static final String REST_PATH = "profile";

    /**
     * Create a request for the method "saveProfile".
     *
     * This request holds the parameters needed by the the conference server.  After setting any
     * optional parameters, call the {@link SaveProfile#execute()} method to invoke the remote
     * operation. <p> {@link
     * SaveProfile#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param content the {@link com.example.sergio.myapplication.backend.domain.conference.model.ProfileForm}
     * @since 1.13
     */
    protected SaveProfile(com.example.sergio.myapplication.backend.domain.conference.model.ProfileForm content) {
      super(Conference.this, "POST", REST_PATH, content, com.example.sergio.myapplication.backend.domain.conference.model.Profile.class);
    }

    @Override
    public SaveProfile setAlt(java.lang.String alt) {
      return (SaveProfile) super.setAlt(alt);
    }

    @Override
    public SaveProfile setFields(java.lang.String fields) {
      return (SaveProfile) super.setFields(fields);
    }

    @Override
    public SaveProfile setKey(java.lang.String key) {
      return (SaveProfile) super.setKey(key);
    }

    @Override
    public SaveProfile setOauthToken(java.lang.String oauthToken) {
      return (SaveProfile) super.setOauthToken(oauthToken);
    }

    @Override
    public SaveProfile setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (SaveProfile) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public SaveProfile setQuotaUser(java.lang.String quotaUser) {
      return (SaveProfile) super.setQuotaUser(quotaUser);
    }

    @Override
    public SaveProfile setUserIp(java.lang.String userIp) {
      return (SaveProfile) super.setUserIp(userIp);
    }

    @Override
    public SaveProfile set(String parameterName, Object value) {
      return (SaveProfile) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "unregisterFromConference".
   *
   * This request holds the parameters needed by the conference server.  After setting any optional
   * parameters, call the {@link UnregisterFromConference#execute()} method to invoke the remote
   * operation.
   *
   * @param websafeConferenceKey
   * @return the request
   */
  public UnregisterFromConference unregisterFromConference(java.lang.String websafeConferenceKey) throws java.io.IOException {
    UnregisterFromConference result = new UnregisterFromConference(websafeConferenceKey);
    initialize(result);
    return result;
  }

  public class UnregisterFromConference extends ConferenceRequest<com.example.sergio.myapplication.backend.domain.conference.model.WrappedBoolean> {

    private static final String REST_PATH = "conference/{websafeConferenceKey}/registration";

    /**
     * Create a request for the method "unregisterFromConference".
     *
     * This request holds the parameters needed by the the conference server.  After setting any
     * optional parameters, call the {@link UnregisterFromConference#execute()} method to invoke the
     * remote operation. <p> {@link UnregisterFromConference#initialize(com.google.api.client.googleap
     * is.services.AbstractGoogleClientRequest)} must be called to initialize this instance
     * immediately after invoking the constructor. </p>
     *
     * @param websafeConferenceKey
     * @since 1.13
     */
    protected UnregisterFromConference(java.lang.String websafeConferenceKey) {
      super(Conference.this, "DELETE", REST_PATH, null, com.example.sergio.myapplication.backend.domain.conference.model.WrappedBoolean.class);
      this.websafeConferenceKey = com.google.api.client.util.Preconditions.checkNotNull(websafeConferenceKey, "Required parameter websafeConferenceKey must be specified.");
    }

    @Override
    public UnregisterFromConference setAlt(java.lang.String alt) {
      return (UnregisterFromConference) super.setAlt(alt);
    }

    @Override
    public UnregisterFromConference setFields(java.lang.String fields) {
      return (UnregisterFromConference) super.setFields(fields);
    }

    @Override
    public UnregisterFromConference setKey(java.lang.String key) {
      return (UnregisterFromConference) super.setKey(key);
    }

    @Override
    public UnregisterFromConference setOauthToken(java.lang.String oauthToken) {
      return (UnregisterFromConference) super.setOauthToken(oauthToken);
    }

    @Override
    public UnregisterFromConference setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (UnregisterFromConference) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public UnregisterFromConference setQuotaUser(java.lang.String quotaUser) {
      return (UnregisterFromConference) super.setQuotaUser(quotaUser);
    }

    @Override
    public UnregisterFromConference setUserIp(java.lang.String userIp) {
      return (UnregisterFromConference) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String websafeConferenceKey;

    /**

     */
    public java.lang.String getWebsafeConferenceKey() {
      return websafeConferenceKey;
    }

    public UnregisterFromConference setWebsafeConferenceKey(java.lang.String websafeConferenceKey) {
      this.websafeConferenceKey = websafeConferenceKey;
      return this;
    }

    @Override
    public UnregisterFromConference set(String parameterName, Object value) {
      return (UnregisterFromConference) super.set(parameterName, value);
    }
  }

  /**
   * Builder for {@link Conference}.
   *
   * <p>
   * Implementation is not thread-safe.
   * </p>
   *
   * @since 1.3.0
   */
  public static final class Builder extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient.Builder {

    /**
     * Returns an instance of a new builder.
     *
     * @param transport HTTP transport, which should normally be:
     *        <ul>
     *        <li>Google App Engine:
     *        {@code com.google.api.client.extensions.appengine.http.UrlFetchTransport}</li>
     *        <li>Android: {@code newCompatibleTransport} from
     *        {@code com.google.api.client.extensions.android.http.AndroidHttp}</li>
     *        <li>Java: {@link com.google.api.client.googleapis.javanet.GoogleNetHttpTransport#newTrustedTransport()}
     *        </li>
     *        </ul>
     * @param jsonFactory JSON factory, which may be:
     *        <ul>
     *        <li>Jackson: {@code com.google.api.client.json.jackson2.JacksonFactory}</li>
     *        <li>Google GSON: {@code com.google.api.client.json.gson.GsonFactory}</li>
     *        <li>Android Honeycomb or higher:
     *        {@code com.google.api.client.extensions.android.json.AndroidJsonFactory}</li>
     *        </ul>
     * @param httpRequestInitializer HTTP request initializer or {@code null} for none
     * @since 1.7
     */
    public Builder(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
        com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
      super(
          transport,
          jsonFactory,
          DEFAULT_ROOT_URL,
          DEFAULT_SERVICE_PATH,
          httpRequestInitializer,
          false);
    }

    /** Builds a new instance of {@link Conference}. */
    @Override
    public Conference build() {
      return new Conference(this);
    }

    @Override
    public Builder setRootUrl(String rootUrl) {
      return (Builder) super.setRootUrl(rootUrl);
    }

    @Override
    public Builder setServicePath(String servicePath) {
      return (Builder) super.setServicePath(servicePath);
    }

    @Override
    public Builder setHttpRequestInitializer(com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
      return (Builder) super.setHttpRequestInitializer(httpRequestInitializer);
    }

    @Override
    public Builder setApplicationName(String applicationName) {
      return (Builder) super.setApplicationName(applicationName);
    }

    @Override
    public Builder setSuppressPatternChecks(boolean suppressPatternChecks) {
      return (Builder) super.setSuppressPatternChecks(suppressPatternChecks);
    }

    @Override
    public Builder setSuppressRequiredParameterChecks(boolean suppressRequiredParameterChecks) {
      return (Builder) super.setSuppressRequiredParameterChecks(suppressRequiredParameterChecks);
    }

    @Override
    public Builder setSuppressAllChecks(boolean suppressAllChecks) {
      return (Builder) super.setSuppressAllChecks(suppressAllChecks);
    }

    /**
     * Set the {@link ConferenceRequestInitializer}.
     *
     * @since 1.12
     */
    public Builder setConferenceRequestInitializer(
        ConferenceRequestInitializer conferenceRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(conferenceRequestInitializer);
    }

    @Override
    public Builder setGoogleClientRequestInitializer(
        com.google.api.client.googleapis.services.GoogleClientRequestInitializer googleClientRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(googleClientRequestInitializer);
    }
  }
}
