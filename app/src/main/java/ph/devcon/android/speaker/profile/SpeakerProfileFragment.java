package ph.devcon.android.speaker.profile;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import ph.devcon.android.R;

/**
 * Created by lope on 9/13/14.
 */
public class SpeakerProfileFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_speaker_profile, container, false);
        ButterKnife.inject(this, rootView);
        return rootView;
    }

}