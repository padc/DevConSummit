package ph.devcon.android.attendee;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ph.devcon.android.R;
import ph.devcon.android.util.Util;

/**
 * Created by lope on 10/9/14.
 */
public class AttendeeDetailsFragment extends Fragment {
    @InjectView(R.id.img_background_top)
    ImageView imgBackgroundTop;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater
                .inflate(R.layout.fragment_attendee_details, container, false);
        ButterKnife.inject(this, rootView);
        Bitmap icon = BitmapFactory.decodeResource(getActivity().getResources(),
                R.drawable.sample_large);
        imgBackgroundTop.setImageBitmap(Util.blurBitmap(getActivity(), icon));
        return rootView;
    }

    protected View buildFooterView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.footer_standard, null);
    }

}