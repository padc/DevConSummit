package ph.devcon.android.attendee.controlller;

import ph.devcon.android.attendee.api.AttendeeBaseResponse;
import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by lope on 10/31/2014.
 */
public interface AttendeeController {
    @FormUrlEncoded
    @POST("/attendees")
    void fetchAttendees(@Field("authentication_token") String token, Callback<AttendeeBaseResponse> callback);
}
