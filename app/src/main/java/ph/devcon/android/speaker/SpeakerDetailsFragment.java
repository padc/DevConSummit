package ph.devcon.android.speaker;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.common.base.Optional;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ph.devcon.android.DevConApplication;
import ph.devcon.android.R;
import ph.devcon.android.speaker.db.Speaker;
import ph.devcon.android.speaker.service.SpeakerService;
import ph.devcon.android.util.Util;

/**
 * Created by lope on 9/13/14.
 */
public class SpeakerDetailsFragment extends android.support.v4.app.Fragment {
    public static final String ID = "speaker_id";

    @InjectView(R.id.img_background_top)
    ImageView imgBackgroundTop;

    @InjectView(R.id.img_speaker)
    ImageView imgSpeaker;

    @InjectView(R.id.txt_speaker_name)
    TextView txtSpeakerName;

    @InjectView(R.id.txt_position)
    TextView txtPosition;

    @InjectView(R.id.txt_twitter)
    TextView txtTwitter;

    @InjectView(R.id.txt_website)
    TextView txtWebsite;

    @InjectView(R.id.txt_speaker_type)
    TextView txtSpeakerType;

    @InjectView(R.id.txt_talk_title)
    TextView txtTalkTitle;

    @InjectView(R.id.txt_talk_title)
    TextView txtAboutTitle;

    @InjectView(R.id.txt_talk_title)
    TextView txtAboutContent;

    @Inject
    SpeakerService speakerService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater
                .inflate(R.layout.fragment_speaker_profile, container, false);
        DevConApplication.getInstance().injectMembers(this);
        ButterKnife.inject(this, rootView);
        Bitmap icon = BitmapFactory.decodeResource(getActivity().getResources(),
                R.drawable.sample_large);
        imgBackgroundTop.setImageBitmap(Util.blurBitmap(getActivity(), icon));
        Speaker speaker = speakerService.getSpeaker(getArguments().getInt(ID));
        init(speaker);
        return rootView;
    }

    public void init(Speaker speaker) {
        Optional<Speaker> speakerOptional = Optional.of(speaker);
        if (speakerOptional.isPresent()) {
            txtSpeakerName.setText(speaker.getFullName());
            txtPosition.setText(speaker.getPositionAndCompany());
            txtTwitter.setText(speaker.getTwitterHandle());
            txtWebsite.setText(speaker.getWebsite());
            txtSpeakerType.setText(speaker.getSpeakerType());
            txtTalkTitle.setText(speaker.getMainTalkTitle());
            txtAboutTitle.setText(speaker.getAboutTitle());
            txtAboutContent.setText(speaker.getDescription());
            if (!Util.isNullOrEmpty(speaker.getPhotoUrl()))
                Picasso.with(getActivity()).load(speaker.getPhotoUrl()).into(imgSpeaker);
        }
    }
}