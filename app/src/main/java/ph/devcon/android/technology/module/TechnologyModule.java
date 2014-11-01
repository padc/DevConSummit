package ph.devcon.android.technology.module;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ph.devcon.android.base.DatabaseHelper;
import ph.devcon.android.technology.db.TechnologyDao;

/**
 * Created by lope on 10/29/14.
 */
@Module(library = true)
public class TechnologyModule {
    Context mContext;

    public TechnologyModule(Context context) {
        mContext = context;
    }

    @Provides
    @Singleton
    public TechnologyDao provideTechnologyDao() {
        return DatabaseHelper.getInstance(mContext).getTechnologyDao();
    }
}