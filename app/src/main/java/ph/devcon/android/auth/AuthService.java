/*
 * Copyright (C) 2014 Philippine Android Developers Community
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

    public boolean isAuthenticated();

    public static interface AuthCallback {
        public void onAuthenticated(String token);

        public void onAuthenticationFailed(Integer statusCode, String message);
    }

    public static class TokenNotExistsException extends Exception {

    }
}
