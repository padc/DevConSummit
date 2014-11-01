package ph.devcon.android.profile.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by lope on 11/1/2014.
 */
public class EditProfileBaseResponse {

    @Expose
    private ProfileAPI profile;

    @SerializedName("status_code")
    @Expose
    private Integer statusCode;

    /**
     * @return The profile
     */
    public ProfileAPI getProfile() {
        return profile;
    }

    /**
     * @param profile The profile
     */
    public void setProfile(ProfileAPI profile) {
        this.profile = profile;
    }

    /**
     * @return The statusCode
     */
    public Integer getStatusCode() {
        return statusCode;
    }

    /**
     * @param statusCode The status_code
     */
    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

}