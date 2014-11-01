package ph.devcon.android.profile.event;

import ph.devcon.android.profile.db.Profile;

/**
 * Created by lope on 10/29/14.
 */
public class FetchedProfileEvent {
    public Profile profile;

    public FetchedProfileEvent(Profile profile) {
        this.profile = profile;
    }
}
