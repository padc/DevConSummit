package ph.devcon.android.speaker;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ph.devcon.android.R;
import ph.devcon.android.util.Util;

/**
 * Created by lope on 9/13/14.
 */
public class SpeakerDetailsFragment extends android.support.v4.app.Fragment {
    @InjectView(R.id.img_background_top)
    ImageView imgBackgroundTop;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater
                .inflate(R.layout.fragment_speaker_profile, container, false);
        ButterKnife.inject(this, rootView);
        Bitmap icon = BitmapFactory.decodeResource(getActivity().getResources(),
                R.drawable.sample_large);
        imgBackgroundTop.setImageBitmap(Util.blurBitmap(getActivity(), icon));
        return rootView;
    }
}