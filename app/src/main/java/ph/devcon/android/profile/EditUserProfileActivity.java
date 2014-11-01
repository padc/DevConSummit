package ph.devcon.android.profile;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.EditText;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ph.devcon.android.R;

/**
 * Created by lope on 9/13/14.
 */
public class EditUserProfileActivity extends ActionBarActivity {

    @InjectView(R.id.edt_company_position)
    EditText edtCompanyPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_edit_profile);
        ButterKnife.inject(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

}