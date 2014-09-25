package ph.devcon.android.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import butterknife.OnClick;
import ph.devcon.android.R;
import ph.devcon.android.navigation.MainActivity;

/**
 * Created by lope on 9/16/14.
 */
public class LoginActivity extends Activity {

    @OnClick(R.id.btn_login)
    public void onClickLogin(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

}
