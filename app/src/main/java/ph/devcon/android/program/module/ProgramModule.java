package ph.devcon.android.program.module;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ph.devcon.android.auth.module.AuthModule;
import ph.devcon.android.base.DatabaseHelper;
import ph.devcon.android.base.module.APIModule;
import ph.devcon.android.base.module.EventBusModule;
import ph.devcon.android.program.ProgramFragment;
import ph.devcon.android.program.db.ProgramDao;
import ph.devcon.android.program.job.FetchProgramListJob;
import retrofit.RestAdapter;

/**
 * Created by lope on 10/29/14.
 */
@Module(injects = {ProgramFragment.class, FetchProgramListJob.class},
        includes = {APIModule.class, AuthModule.class, EventBusModule.class})
public class ProgramModule {
    Context mContext;

    public ProgramModule(Context context) {
        mContext = context;
    }

    @Provides
    @Singleton
    public ProgramDao provideProgramDao(RestAdapter restAdapter) {
        return DatabaseHelper.getInstance(mContext).getProgramDao();
    }

}