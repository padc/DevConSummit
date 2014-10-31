package ph.devcon.android.sponsor.event;

import java.util.List;

import ph.devcon.android.sponsor.db.Sponsor;

/**
 * Created by lope on 11/1/2014.
 */
public class FetchedSponsorListEvent {
    List<Sponsor> sponsors;

    public FetchedSponsorListEvent(List<Sponsor> sponsors) {
        this.sponsors = sponsors;
    }
}
