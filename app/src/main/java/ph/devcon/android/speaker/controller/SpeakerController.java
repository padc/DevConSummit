package ph.devcon.android.speaker.controller;

import ph.devcon.android.speaker.api.SpeakerBaseResponse;
import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by lope on 10/29/14.
 * <p/>
 */
public interface SpeakerController {
    @FormUrlEncoded
    @POST("/speakers")
    void fetchSpeakers(@Field("authentication_token") String token, Callback<SpeakerBaseResponse> callback);
}
