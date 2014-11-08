package ph.devcon.android.settings;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by lope on 11/8/14.
 */
@Module(library = true)
public class SettingsModule {
    Context mContext;

    public SettingsModule(Context context) {
        mContext = context;
    }

    @Provides
    @Singleton
    public SettingsService provideSettingsService() {
        return new SettingsServiceImpl(mContext);
    }
}
