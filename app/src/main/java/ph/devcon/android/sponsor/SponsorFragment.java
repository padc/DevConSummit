package ph.devcon.android.sponsor;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.applidium.headerlistview.HeaderListView;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.greenrobot.event.EventBus;
import ph.devcon.android.DevConApplication;
import ph.devcon.android.R;
import ph.devcon.android.sponsor.adapter.SponsorSectionAdapter;
import ph.devcon.android.sponsor.db.Sponsor;
import ph.devcon.android.sponsor.event.FetchedSponsorListEvent;
import ph.devcon.android.sponsor.service.SponsorService;

/**
 * Created by lope on 9/16/14.
 */
public class SponsorFragment extends Fragment {

    @InjectView(R.id.lvw_sponsors)
    HeaderListView lvwSponsors;

    @Inject
    EventBus eventBus;

    @Inject
    SponsorService sponsorService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sponsor, container, false);
        DevConApplication.injectMembers(this);
        ButterKnife.inject(this, rootView);
        if (!eventBus.isRegistered(this)) {
            eventBus.register(this);
        }
        if (sponsorService.isCacheValid()) {
            sponsorService.populateFromCache(getLoaderManager(), savedInstanceState);
        } else {
            sponsorService.populateFromAPI();
        }
        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        eventBus.unregister(this);
    }

    public void setSponsorList(List<Sponsor> sponsorList) {
        if (sponsorList != null && !sponsorList.isEmpty()) {
            lvwSponsors.setAdapter(new SponsorSectionAdapter(getActivity(),
                    sponsorService.buildMultimap(sponsorList)));
        }
    }

    public void onEventMainThread(FetchedSponsorListEvent event) {
        setSponsorList(event.sponsors);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

}