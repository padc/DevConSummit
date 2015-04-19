/*
 * Copyright (C) 2014 Philippine Android Developers Community
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ph.devcon.android.navigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import net.hockeyapp.android.CrashManager;
import net.hockeyapp.android.UpdateManager;

import javax.inject.Inject;

import ph.devcon.android.DevConApplication;
import ph.devcon.android.R;
import ph.devcon.android.auth.AuthService;
import ph.devcon.android.login.LoginActivity;

public class MainActivity extends BaseDevConActivity {

    @Inject
    AuthService authService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DevConApplication.injectMembers(this);
        checkForUpdates();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkForCrashes();
    }

    private void checkForCrashes() {
        CrashManager.register(this, getString(R.string.hockeyapp_id));
    }

    private void checkForUpdates() {
        // Remove this for store / production builds!
        UpdateManager.register(this, getString(R.string.hockeyapp_id));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case R.id.action_signout:
                authService.logout();
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }
}
