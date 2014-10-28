package ph.devcon.android.program.controller;

import java.util.List;

import ph.devcon.android.program.api.ProgramAPI;
import retrofit.Callback;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by lope on 10/27/2014.
 */
public interface ProgramController {
    @POST("/programs")
    void listPrograms(@Path("token") String token, Callback<List<ProgramAPI>> callback);
}
