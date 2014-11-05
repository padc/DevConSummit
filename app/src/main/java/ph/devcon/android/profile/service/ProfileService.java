package ph.devcon.android.profile.service;

import ph.devcon.android.base.service.BaseAPICacheService;
import ph.devcon.android.profile.api.EditProfileBaseResponse;
import ph.devcon.android.profile.db.Profile;

/**
 * Created by lope on 11/1/2014.
 */
public interface ProfileService extends BaseAPICacheService<Profile, EditProfileBaseResponse> {
    public Profile getUserProfile(int id);

    public void updateAPI(Profile profile);
}
