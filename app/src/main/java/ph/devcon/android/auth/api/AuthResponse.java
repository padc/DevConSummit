package ph.devcon.android.auth.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by lope on 10/28/14.
 */
public class AuthResponse {

    @SerializedName("status_code")
    @Expose
    private Integer statusCode;

    @Expose
    private String message;

    @SerializedName("authentication_token")
    @Expose
    private String authenticationToken;

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAuthenticationToken() {
        return authenticationToken;
    }

    public void setAuthenticationToken(String authenticationToken) {
        this.authenticationToken = authenticationToken;
    }

}