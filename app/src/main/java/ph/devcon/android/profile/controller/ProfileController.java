package ph.devcon.android.profile.controller;

import ph.devcon.android.profile.api.EditProfileBaseResponse;
import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by lope on 10/27/2014.
 */
public interface ProfileController {
    @FormUrlEncoded
    @POST("/profile")
    void fetchProfile(@Field("authentication_token") String token, Callback<EditProfileBaseResponse> callback);
}
