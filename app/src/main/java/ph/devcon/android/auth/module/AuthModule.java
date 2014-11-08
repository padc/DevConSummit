package ph.devcon.android.auth.module;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ph.devcon.android.auth.AuthService;
import ph.devcon.android.auth.AuthServiceImpl;
import ph.devcon.android.base.module.APIModule;
import retrofit.RestAdapter;

/**
 * Created by lope on 10/28/14.
 */
@Module(includes = APIModule.class,
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