package ph.devcon.android.navigation;

import android.os.Bundle;
import android.support.v4.view.GravityCompat;

import ph.devcon.android.R;

public class MainActivity extends BaseDevConActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDrawerLayout.openDrawer(GravityCompat.START);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }
}
