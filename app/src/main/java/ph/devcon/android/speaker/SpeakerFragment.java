package ph.devcon.android.speaker;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ph.devcon.android.R;
import ph.devcon.android.speaker.adapter.SpeakerAdapter;
import ph.devcon.android.speaker.db.Speaker;

/**
 * Created by lope on 9/13/14.
 */
public class SpeakerFragment extends Fragment {

    @InjectView(R.id.lvw_speakers)
    ListView lvwSpeaker;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_speaker, container, false);
        ButterKnife.inject(this, rootView);
        List<Speaker> speakers = new ArrayList<Speaker>();
        Speaker speaker = new Speaker();
        speaker.setName("Lope Emano");
        speakers.add(speaker);
        lvwSpeaker.setAdapter(new SpeakerAdapter(getActivity(), speakers));
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
//        ((MainActivity) activity).onSectionAttached(
//                getArguments().getInt(ARG_SECTION_NUMBER));
    }
}