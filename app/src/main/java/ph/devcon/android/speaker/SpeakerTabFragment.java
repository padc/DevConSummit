package ph.devcon.android.speaker;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.viewpagerindicator.TabPageIndicator;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ph.devcon.android.R;
import ph.devcon.android.navigation.BaseDevConActivity;
import ph.devcon.android.speaker.fragment.AllSpeakerFragment;
import ph.devcon.android.speaker.fragment.PanelOnlyFragment;
import ph.devcon.android.speaker.fragment.SpeakerOnlyFragment;

/**
 * Created by lope on 9/13/14.
 */
public class SpeakerTabFragment extends Fragment {

    @InjectView(R.id.pager)
    ViewPager pager;
    @InjectView(R.id.indicator)
    TabPageIndicator indicator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_speaker, container, false);
        ButterKnife.inject(this, rootView);

        FragmentPagerAdapter adapter = new ViewSpeakerTypesAdapter(((BaseDevConActivity) getActivity()).getSupportFragmentManager());
        pager.setAdapter(adapter);
        indicator.setViewPager(pager);
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
//        ((MainActivity) activity).onSectionAttached(
//                getArguments().getInt(ARG_SECTION_NUMBER));
    }

    class ViewSpeakerTypesAdapter extends FragmentPagerAdapter {
        public ViewSpeakerTypesAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new AllSpeakerFragment();
                case 1:
                    return new SpeakerOnlyFragment();
                case 2:
                    return new PanelOnlyFragment();
                default:
                    try {
                        throw new Exception("Fragment position non existent!");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "All";
                case 1:
                    return "Speakers";
                case 2:
                    return "Panels";
                default:
                    try {
                        throw new Exception("Fragment position non existent!");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}