package ph.devcon.android.attendee;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import ph.devcon.android.R;

/**
 * Created by lope on 10/9/14.
 */
public class AttendeeDetailsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater
                .inflate(R.layout.fragment_attendee_details, container, false);
        ButterKnife.inject(this, container);
        return rootView;
    }

    protected View buildFooterView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.footer_standard, null);
    }

}