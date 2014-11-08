package ph.devcon.android.navigation;

import android.os.Bundle;

import ph.devcon.android.R;

public class MainActivity extends BaseDevConActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }
}
