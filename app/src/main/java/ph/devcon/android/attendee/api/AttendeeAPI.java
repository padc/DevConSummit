package ph.devcon.android.attendee.api;

import com.google.gson.annotations.Expose;

import ph.devcon.android.user.api.UserAPI;

/**
 * Created by lope on 10/31/2014.
 */
public class AttendeeAPI {
    @Expose
    private UserAPI user;

    public UserAPI getUser() {
        return user;
    }

    public void setUser(UserAPI user) {
        this.user = user;
    }
}
