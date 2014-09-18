package ph.devcon.android.sponsor;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ph.devcon.android.R;
import ph.devcon.android.sponsor.db.Sponsor;

/**
 * Created by lope on 9/16/14.
 */
public class SponsorFragment extends Fragment {

    @InjectView(R.id.lvw_speakers)
    ListView lvwSpeaker;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sponsor, container, false);
        ButterKnife.inject(this, rootView);
        ViewGroup contSponsors = (ViewGroup) rootView.findViewById(R.id.cont_sponsors);
        buildSponsors(contSponsors, new ArrayList<Sponsor>());
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
//        ((MainActivity) activity).onSectionAttached(
//                getArguments().getInt(ARG_SECTION_NUMBER));
    }

    private void buildSponsors(ViewGroup contSponsors, List<Sponsor> sponsors) {
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        for (Sponsor sponsor : sponsors) {
            View itemSponsorView = inflater.inflate(R.layout.item_sponsor, contSponsors);
            ViewHolder holder = new ViewHolder();
            ButterKnife.inject(holder, itemSponsorView);
            holder.imgSponsorLogo.setImageBitmap(BitmapFactory.decodeByteArray(sponsor.getSponsorIcon(), 0, (sponsor.getSponsorIcon()).length));
        }
    }

    static class ViewHolder {
        @InjectView(R.id.img_sponsor_logo)
        ImageView imgSponsorLogo;
    }
}