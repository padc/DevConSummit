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
        String username = "lope";
        String password = "";
        final String userAndPassword = username + ":" + password;
        return "Basic " + Base64.encodeToString(userAndPassword.getBytes(), Base64.NO_WRAP);
    }
}
