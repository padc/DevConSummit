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

import android.content.Context;
import android.content.SharedPreferences;

import com.google.common.base.Optional;

import ph.devcon.android.auth.api.AuthResponse;
import ph.devcon.android.auth.controller.AuthController;
import ph.devcon.android.util.Util;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by lope on 10/28/14.
 */
public class AuthServiceImpl implements AuthService {
    SharedPreferences mPrefs;
    RestAdapter mRestAdapter;

    public AuthServiceImpl(Context context, RestAdapter restAdapter) {
        mPrefs = new ObscuredSharedPreferences(context, context.getSharedPreferences(KEY_AUTH_PREFS, Context.MODE_PRIVATE));
        mRestAdapter = restAdapter;
    }

    @Override
    public void authenticate(String email, String password, final AuthCallback authCallback) {
        AuthController authController = mRestAdapter.create(AuthController.class);
        authController.authenticate(email, password, new Callback<AuthResponse>() {
            @Override
            public void success(AuthResponse authResponse, Response response) {
                Optional<AuthResponse> authResponseOptional = Optional.of(authResponse);
                if (authResponseOptional.isPresent()) {
                    if (authResponse.getStatusCode().equals(STATUS_CODE_OK)) {
                        if (!Util.isNullOrEmpty(authResponse.getAuthenticationToken())) {
                            String token = authResponse.getAuthenticationToken();
                            authCallback.onAuthenticated(token);
                            setCachedToken(token);
                        } else {
                            authCallback.onAuthenticationFailed(STATUS_CODE_UNKNOWN, "Received null auth token");
                        }
                    } else {
                        authCallback.onAuthenticationFailed(authResponse.getStatusCode(), authResponse.getMessage());
                    }
                } else {
                    authCallback.onAuthenticationFailed(STATUS_CODE_UNKNOWN, "Received null response from server");
                }
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                authCallback.onAuthenticationFailed(STATUS_CODE_UNKNOWN, retrofitError.getMessage());
            }
        });
    }

    @Override
    public String getCachedToken() throws TokenNotExistsException {
        String token = mPrefs.getString(PREF_KEY_AUTH_TOKEN, "");
        if (!Util.isNullOrEmpty(token)) {
            return token;
        } else {
            throw new TokenNotExistsException();
        }
    }

    @Override
    public void setCachedToken(String token) {
        if (!Util.isNullOrEmpty(token))
            mPrefs.edit().putString(PREF_KEY_AUTH_TOKEN, token).commit();
    }

    @Override
    public boolean isAuthenticated() {
        Boolean isAuthenticated = false;
        try {
            if (!Util.isNullOrEmpty(getCachedToken())) {
                isAuthenticated = true;
            }
        } catch (TokenNotExistsException e) {
            e.printStackTrace();
        }
        return isAuthenticated;
    }
}
