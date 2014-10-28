package ph.devcon.android.auth.controller;

import ph.devcon.android.auth.api.AuthResponse;
import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by lope on 10/27/2014.
 */
public interface AuthController {
    @FormUrlEncoded
    @POST("/tokens")
    void authenticate(@Field("email") String email, @Field("password") String password, Callback<AuthResponse> authResponseCallBack);
}
