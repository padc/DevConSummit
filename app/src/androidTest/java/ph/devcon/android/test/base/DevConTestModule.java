package ph.devcon.android.test.base;

import dagger.Module;
import ph.devcon.android.auth.module.AuthModule;
import ph.devcon.android.test.auth.AuthAPITestCase;

/**
 * Created by lope on 11/16/14.
 */
@Module(injects = AuthAPITestCase.class,
        includes = {AuthModule.class},
        library = true,
        complete = true,
        overrides = true)
public class DevConTestModule {
}
