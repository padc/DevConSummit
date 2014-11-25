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

package ph.devcon.android.auth.module;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ph.devcon.android.auth.AuthService;
import ph.devcon.android.auth.AuthServiceImpl;
import ph.devcon.android.base.module.APIModule;
import ph.devcon.android.navigation.MainActivity;
import ph.devcon.android.profile.EditUserProfileActivity;
import retrofit.RestAdapter;

/**
 * Created by lope on 10/28/14.
 */
@Module(injects = {MainActivity.class},
        includes = APIModule.class,
        library = true)
public class AuthModule {
    Context mContext;

    public AuthModule(Context context) {
        mContext = context;
    }

    @Provides
    @Singleton
    public AuthService provideAuthService(RestAdapter restAdapter) {
        return new AuthServiceImpl(mContext, restAdapter);
    }

}