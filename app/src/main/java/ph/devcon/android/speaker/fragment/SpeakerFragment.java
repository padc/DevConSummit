package ph.devcon.android.speaker.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnItemClick;
import ph.devcon.android.R;
import ph.devcon.android.speaker.SpeakerDetailsActivity;
import ph.devcon.android.speaker.adapter.SpeakerAdapter;
import ph.devcon.android.speaker.db.Speaker;

/**
 * Created by lope on 9/29/14.
 */
public class SpeakerFragment extends Fragment {
    @InjectView(R.id.lvw_speakers)
    ListView lvwSpeaker;

    @OnItemClick(R.id.lvw_speakers)
    public void onItemClick(int position) {
        Intent intent = new Intent(getActivity(), SpeakerDetailsActivity.class);
        startActivity(intent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_speakers_all, container, false);
        ButterKnife.inject(this, rootView);
        lvwSpeaker.addFooterView(buildFooterView(inflater));
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onStart() {
        List<Speaker> speakers = new ArrayList<Speaker>();
        Speaker speaker = new Speaker();
        speaker.setFirstName("Lope");
        speaker.setLastName("Emano");
        speakers.add(speaker);
        speakers.add(speaker);
        speakers.add(speaker);
        speakers.add(speaker);
        speakers.add(speaker);
        speakers.add(speaker);
        speakers.add(speaker);
        lvwSpeaker.setAdapter(new SpeakerAdapter(getActivity(), speakers));
        super.onStart();
    }

    protected View buildFooterView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.footer_standard, null);
    }
}
