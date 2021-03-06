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

package ph.devcon.android.login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import ph.devcon.android.DevConApplication;
import ph.devcon.android.R;
import ph.devcon.android.auth.AuthService;
import ph.devcon.android.navigation.MainActivity;
import ph.devcon.android.profile.event.FetchedProfileEvent;
import ph.devcon.android.profile.event.FetchedProfileFailedEvent;
import ph.devcon.android.profile.service.ProfileService;
import ph.devcon.android.util.Util;

/**
 * Created by lope on 9/16/14.
 */
public class LoginActivity extends Activity {

    @InjectView(R.id.edt_email_address)
    EditText edtEmailAddress;

    @InjectView(R.id.edt_password)
    EditText edtPassword;

    @InjectView(R.id.btn_login)
    Button btnLogin;

    @Inject
    AuthService authService;

    @Inject
    ProfileService profileService;

    @Inject
    EventBus eventBus;

    ProgressDialog authProgressDialog;

    @OnClick(R.id.btn_login)
    public void onClickLogin(View view) {
        if (Util.isNetworkAvailable(this)) {
            String email = String.valueOf(edtEmailAddress.getText());
            String password = String.valueOf(edtPassword.getText());
            email = "haifa@devcon.ph";
            password = "password";
            authProgressDialog = new ProgressDialog(this, ProgressDialog.THEME_HOLO_LIGHT);
            authProgressDialog.setIndeterminate(false);
            authProgressDialog.setProgressStyle(ProgressDialog.THEME_DEVICE_DEFAULT_LIGHT);
            authProgressDialog.setMessage(getString(R.string.authenticating));
            authProgressDialog.show();
            authService.authenticate(email, password, new AuthService.AuthCallback() {
                @Override
                public void onAuthenticated(String token) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            authProgressDialog.setMessage("Fetching user profile..");
                            profileService.populateFromAPI();
                        }
                    });
                }

                @Override
                public void onAuthenticationFailed(final Integer statusCode, final String message) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
                            authProgressDialog.dismiss();
                        }
                    });
                }
            });
        } else {
            Toast.makeText(LoginActivity.this, getString(R.string.error_no_network), Toast.LENGTH_LONG).show();
        }
    }

    @OnClick(R.id.txt_forgot_password)
    public void onClickForgotPassword(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://summit.devcon.ph/"));
        startActivity(intent);
    }

    @OnClick(R.id.txt_support)
    public void onClickContactSupport(View view) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setData(Uri.parse("mailto:"));
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_EMAIL, "support@devcon.ph");
        intent.putExtra(Intent.EXTRA_SUBJECT, "User Registration");
        intent.putExtra(Intent.EXTRA_TEXT, "Hi! I'd like a new account for DevCon Summit");
        startActivity(Intent.createChooser(intent, "Send Email"));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DevConApplication.injectMembers(this);
        if (authService.isAuthenticated()) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        if (!eventBus.isRegistered(this)) {
            eventBus.register(this);
        }
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        init();
    }

    public void onEventMainThread(FetchedProfileEvent fetchedProfileEvent) {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        authProgressDialog.dismiss();
        finish();
    }

    public void onEventMainThread(FetchedProfileFailedEvent fetchedProfileFailedEvent) {
        Toast.makeText(LoginActivity.this, String.valueOf(fetchedProfileFailedEvent), Toast.LENGTH_LONG).show();
        authProgressDialog.dismiss();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        eventBus.unregister(this);
    }

    protected void init() {
        Typeface credentialsFont = Typeface.createFromAsset(getAssets(), "fonts/SourceSansPro-Light.otf");
        edtEmailAddress.setTypeface(credentialsFont);
        edtPassword.setTypeface(credentialsFont);
        Typeface buttonFont = Typeface.createFromAsset(getAssets(), "fonts/SourceSansPro-Bold.otf");
        btnLogin.setTypeface(buttonFont);
    }

}
