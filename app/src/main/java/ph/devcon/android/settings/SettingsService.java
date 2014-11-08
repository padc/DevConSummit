package ph.devcon.android.settings;

/**
 * Created by lope on 11/8/14.
 */
public interface SettingsService {
    public static final String KEY_IS_ONBOARDING_DONE = "pref_key_is_onboarding_done";

    public void setIsOnboardingDone(boolean isDone);

    public boolean isOnboardingDone();

}
