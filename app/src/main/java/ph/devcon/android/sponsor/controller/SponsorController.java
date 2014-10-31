package ph.devcon.android.sponsor.controller;

import ph.devcon.android.sponsor.api.SponsorBaseResponse;
import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by lope on 10/31/2014.
 */
public interface SponsorController {
    @FormUrlEncoded
    @POST("/sponsors")
    void fetchSponsors(@Field("authentication_token") String token, Callback<SponsorBaseResponse> callback);
}
