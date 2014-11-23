package ph.devcon.android.test.base;

import dagger.Module;
import ph.devcon.android.auth.module.AuthModule;
import ph.devcon.android.profile.module.ProfileModule;
import ph.devcon.android.test.auth.AuthAPITestCase;
import ph.devcon.android.test.profile.UserFacebookUrlTestCase;
import ph.devcon.android.test.profile.UserProfileAPITestCase;
import ph.devcon.android.test.profile.UserWebUrlTestCase;

/**
 * Created by lope on 11/16/14.
 */
@Module(injects = {AuthAPITestCase.class, UserFacebookUrlTestCase.class,
        UserWebUrlTestCase.class, UserProfileAPITestCase.class},
        includes = {AuthModule.class, ProfileModule.class},
        library = true,
        complete = true,
        overrides = true)
public class DevConTestModule {
}
