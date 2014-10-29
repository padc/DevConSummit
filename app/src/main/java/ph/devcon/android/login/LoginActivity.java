package ph.devcon.android.login;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import ph.devcon.android.DevConApplication;
import ph.devcon.android.R;
import ph.devcon.android.auth.AuthService;
import ph.devcon.android.navigation.MainActivity;

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

    @OnClick(R.id.btn_login)
    public void onClickLogin(View view) {
        String email = String.valueOf(edtEmailAddress.getText());
        String password = String.valueOf(edtPassword.getText());
        email = "haifa@devcon.ph";
        password = "password";
        authService.authenticate(email, password, new AuthService.AuthCallback() {
            @Override
            public void onAuthenticated(String token) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onAuthenticationFailed(Integer statusCode, String message) {
                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
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
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        init();
    }

    protected void init() {
        Typeface credentialsFont = Typeface.createFromAsset(getAssets(), "fonts/SourceSansPro-Light.otf");
        edtEmailAddress.setTypeface(credentialsFont);
        edtPassword.setTypeface(credentialsFont);
        Typeface buttonFont = Typeface.createFromAsset(getAssets(), "fonts/SourceSansPro-Bold.otf");
        btnLogin.setTypeface(buttonFont);
    }

}
