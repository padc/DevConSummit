package ph.devcon.android.program.controller;

import ph.devcon.android.program.api.ProgramBaseResponse;
import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by lope on 10/27/2014.
 */
public interface ProgramController {
    @FormUrlEncoded
    @POST("/programs")
    void listPrograms(@Field("authentication_token") String token, Callback<ProgramBaseResponse> callback);
}
