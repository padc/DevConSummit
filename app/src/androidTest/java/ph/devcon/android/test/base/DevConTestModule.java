package ph.devcon.android.test.base;

import android.content.Context;

import dagger.Module;
import ph.devcon.android.auth.module.AuthModule;
import ph.devcon.android.test.auth.AuthTestCase;

/**
 * Created by lope on 11/16/14.
 */
@Module(injects = {AuthTestCase.class},
        includes = {APITestModule.class, AuthModule.class},
        library = true,
        complete = true,
        overrides = true)
public class DevConTestModule {
    Context mContext;

    public DevConTestModule(Context context) {
        mContext = context;
    }

}
