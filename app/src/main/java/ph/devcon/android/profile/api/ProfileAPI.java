package ph.devcon.android.profile.api;

import com.google.gson.annotations.Expose;

import ph.devcon.android.user.api.UserAPI;

/**
 * Created by lope on 11/1/2014.
 */
public class ProfileAPI {

    @Expose
    private UserAPI user;

    /**
     * @return The user
     */
    public UserAPI getUser() {
        return user;
    }

    /**
     * @param user The user
     */
    public void setUser(UserAPI user) {
        this.user = user;
    }

    ;
}