package ph.devcon.android.base.api;

import android.util.Base64;

import retrofit.RequestInterceptor;

/**
 * Created by lope on 10/28/14.
 */
public class ApiRequestInterceptor implements RequestInterceptor {
    @Override
    public void intercept(RequestFacade requestFacade) {
        final String authorizationValue = encodeCredentialsForBasicAuthorization();
        requestFacade.addHeader("Proxy-Authorization", authorizationValue);
    }

    private String encodeCredentialsForBasicAuthorization() {
        String username = "";
        String password = "";
        final String userAndPassword = username + ":" + password;
        return "Basic " + Base64.encodeToString(userAndPassword.getBytes(), Base64.NO_WRAP);
    }
}
