package ph.devcon.android.auth;

/**
 * Created by lope on 10/28/14.
 */
public interface AuthService {
    public static final String KEY_AUTH_PREFS = "auth_prefs";
    public static final String PREF_KEY_AUTH_TOKEN = "auth_token";
    public static final Integer STATUS_CODE_UNKNOWN = 0;
    public static final Integer STATUS_CODE_OK = 200;

    public void authenticate(String username, String password, AuthCallback authCallback);

    public String getCachedToken() throws TokenNotExistsException;

    public void setCachedToken(String token);

    public static interface AuthCallback {
        public void onAuthenticated(String token);

        public void onAuthenticationFailed(Integer statusCode, String message);
    }

    public static class TokenNotExistsException extends Exception {

    }
}
