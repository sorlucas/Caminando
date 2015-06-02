package com.example.sergio.myapplication.backend.spi;

import com.example.sergio.myapplication.backend.Constants;
import com.example.sergio.myapplication.backend.domain.Profile;
import com.example.sergio.myapplication.backend.form.ProfileForm;
import com.example.sergio.myapplication.backend.form.ProfileForm.TeeShirtSize;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.UnauthorizedException;
import com.google.appengine.api.users.User;
import com.googlecode.objectify.Key;

import static com.example.sergio.myapplication.backend.service.OfyService.ofy;

/**
 * WARNING: This generated code is intended as a sample or starting point for using a
 * Google Cloud Endpoints RESTful API with an Objectify entity. It provides no data access
 * restrictions and no data validation.
 * <p/>
 * DO NOT deploy this code unchanged as part of a real application to real users.
 */
@Api(
        name = "conference",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "domain.backend.myapplication.sergio.example.com",
                ownerName = "domain.backend.myapplication.sergio.example.com",
                packagePath = ""        ),
        scopes = { Constants.EMAIL_SCOPE },
        clientIds = {
                Constants.WEB_CLIENT_ID,
                Constants.API_EXPLORER_CLIENT_ID},
        audiences = {Constants.ANDROID_AUDIENCE},
        description = "Conference Central API for creating and querying conferences," +
                " and for creating and getting user Profiles"
)
public class ConferenceApi {

    /*
     * Get the display name from the user's email. For example, if the email is
     * lemoncake@example.com, then the display name becomes "lemoncake."
     */
    private static String extractDefaultDisplayNameFromEmail(String email) {
        return email == null ? null : email.substring(0, email.indexOf("@"));
    }

    /**
     * Creates or updates a Profile object associated with the given user
     * object.
     *
     * @param user
     *            A User object injected by the cloud endpoints.
     * @param profileForm
     *            A ProfileForm object sent from the client form.
     * @return Profile object just created.
     * @throws UnauthorizedException
     *             when the User object is null.
     */
    @ApiMethod(name = "saveProfile", path = "profile", httpMethod = HttpMethod.POST)
    public Profile saveProfile(final User user, ProfileForm profileForm) throws UnauthorizedException {

        // If the user is not logged in, throw an UnauthorizedException
        if (user == null){
            throw new UnauthorizedException("Autority required");
        }

        // Get the userId and mainEmail
        String userId = user.getUserId();
        String mainEmail = user.getEmail();
        String displayName;
        TeeShirtSize teeShirtSize = TeeShirtSize.NOT_SPECIFIED;

        // Set the teeShirtSize to the value sent by the ProfileForm, if sent
        // otherwise leave it as the default value
        if (profileForm.getTeeShirtSize() != null){
            teeShirtSize = profileForm.getTeeShirtSize();
        }

        // If the displayName is null, set it to default value based on the user's email
        if (profileForm.getDisplayName() == null){
            displayName = extractDefaultDisplayNameFromEmail(mainEmail);
        } else {
            // Set the displayName to the value sent by the ProfileForm,
            displayName = profileForm.getDisplayName();
        }

        // Create a new Profile entity from the userId, displayName, mainEmail and teeShirtSize
        Profile profile = new Profile(userId, displayName, mainEmail, teeShirtSize);


        // Save the Profile entity in the datastore
        ofy().save().entity(profile).now();

        // Return the profile
        return profile;
    }

    /**
     * Returns a Profile object associated with the given user object. The cloud
     * endpoints system automatically inject the User object.
     *
     * @param user
     *            A User object injected by the cloud endpoints.
     * @return Profile object.
     * @throws UnauthorizedException
     *             when the User object is null.
     */
    @ApiMethod(name = "getProfile", path = "profile", httpMethod = HttpMethod.GET)
    public Profile getProfile(final User user) throws UnauthorizedException {
        if (user == null) {
            throw new UnauthorizedException("Authorization required");
        }

        // TODO
        // load the Profile Entity
        String userId = ""; // TODO
        Key key = null; // TODO
        Profile profile = null; // TODO load the Profile entity
        return profile;
    }

}