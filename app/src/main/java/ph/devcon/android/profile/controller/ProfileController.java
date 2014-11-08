package ph.devcon.android.profile.controller;

import ph.devcon.android.profile.api.EditProfileBaseResponse;
import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Part;
import retrofit.mime.TypedFile;
import retrofit.mime.TypedString;

/**
 * Created by lope on 10/27/2014.
 */
public interface ProfileController {
    @FormUrlEncoded
    @POST("/profile")
    void fetchProfile(@Field("authentication_token") String token, Callback<EditProfileBaseResponse> callback);

    @Multipart
    @PUT("/profile")
    void editProfile(@Part("authentication_token") TypedString token, @Part("photo") TypedFile photo,
                     @Part("email") TypedString email, @Part("primary_technology") TypedString primaryTechnology,
                     @Part("position") TypedString position, @Part("company") TypedString company,
                     @Part("location") TypedString location, @Part("description") TypedString description,
                     @Part("website") TypedString website, @Part("facebook_url") TypedString facebookUrl,
                     @Part("twitter_handle") TypedString twitterHandle, Callback<EditProfileBaseResponse> callback);

    @Multipart
    @PUT("/profile")
    void editProfile(@Part("authentication_token") TypedString token, @Part("twitter_handle") TypedString twitterHandle,
                     @Part("email") TypedString email, @Part("primary_technology") TypedString primaryTechnology,
                     @Part("position") TypedString position, @Part("company") TypedString company,
                     @Part("location") TypedString location, @Part("description") TypedString description,
                     @Part("website") TypedString website, @Part("facebook_url") TypedString facebookUrl,
                     Callback<EditProfileBaseResponse> callback);

}
