package ph.devcon.android.base.api;

import android.util.Base64;

import retrofit.RequestInterceptor;

/**
 * Created by lope on 10/28/14.
 */
public class ApiRequestInterceptor implements RequestInterceptor {
    @Override
    public void intercept(RequestFacade requestFacade) {
//        final String authorizationValue = encodeCredentialsForBasicAuthorization();
        final String authorizationValue = "bG9wZUBtZXRyaWNicG8uY29tOm1ldHJpY2Jwbw==";
        requestFacade.addHeader("Proxy-Authorization", authorizationValue);
    }

    private String encodeCredentialsForBasicAuthorization() {
        final String userAndPassword = "" + ":" + "";
        return "Basic " + Base64.encodeToString(userAndPassword.getBytes(), Base64.NO_WRAP);
    }
}
