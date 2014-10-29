package ph.devcon.android.speaker.module;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ph.devcon.android.base.DatabaseHelper;
import ph.devcon.android.speaker.db.SpeakerDao;

/**
 * Created by lope on 10/29/14.
 */
@Module(library = true)
public class SpeakerModule {
    Context mContext;

    public SpeakerModule(Context context) {
        mContext = context;
    }

    @Provides
    @Singleton
    public SpeakerDao provideSpeakerDao() {
        return DatabaseHelper.getInstance(mContext).getSpeakerDao();
    }

}