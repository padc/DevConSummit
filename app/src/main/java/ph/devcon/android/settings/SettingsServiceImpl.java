package ph.devcon.android.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by lope on 11/8/14.
 */
public class SettingsServiceImpl implements SettingsService {
    SharedPreferences sharedPreferences;

    public SettingsServiceImpl(Context context) {
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Override
    public void setIsOnboardingDone(boolean isDone) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_IS_ONBOARDING_DONE, isDone);
        editor.commit();
    }

    @Override
    public boolean isOnboardingDone() {
        return sharedPreferences.getBoolean(KEY_IS_ONBOARDING_DONE, false);
    }
}
