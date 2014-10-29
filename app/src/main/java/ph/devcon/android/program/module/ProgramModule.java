package ph.devcon.android.program.module;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ph.devcon.android.base.DatabaseHelper;
import ph.devcon.android.base.module.APIModule;
import ph.devcon.android.program.ProgramFragment;
import ph.devcon.android.program.db.ProgramDao;
import retrofit.RestAdapter;

/**
 * Created by lope on 10/29/14.
 */
@Module(injects = ProgramFragment.class,
        includes = APIModule.class)
public class ProgramModule {
    Context mContext;

    public ProgramModule(Context context) {
        mContext = context;
    }

    @Provides
    @Singleton
    public ProgramDao provideAuthService(RestAdapter restAdapter) {
        return DatabaseHelper.getInstance(mContext).getProgramDao();
    }

}