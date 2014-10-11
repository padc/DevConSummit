package ph.devcon.android.attendee;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;

import butterknife.ButterKnife;
import ph.devcon.android.R;
import ph.devcon.android.navigation.BaseDevConActivity;

/**
 * Created by lope on 10/12/14.
 */
public class AttendeeDetailsActivity extends BaseDevConActivity {

    AttendeeDetailsPagerAdapter mAttendeeDetailsPagerAdapter;
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ButterKnife.inject(this);

        // ViewPager and its adapters use support library
        // fragments, so use getSupportFragmentManager.
        mAttendeeDetailsPagerAdapter =
                new AttendeeDetailsPagerAdapter(
                        getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mAttendeeDetailsPagerAdapter);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_attendee_details;
    }

    protected View buildFooterView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.footer_standard, null);
    }

    // Since this is an object collection, use a FragmentStatePagerAdapter,
// and NOT a FragmentPagerAdapter.
    public static class AttendeeDetailsPagerAdapter extends FragmentStatePagerAdapter {
        public AttendeeDetailsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = new AttendeeDetailsFragment();
            Bundle args = new Bundle();
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            return 100;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            // TODO
            return "OBJECT " + (position + 1);
        }
    }

}