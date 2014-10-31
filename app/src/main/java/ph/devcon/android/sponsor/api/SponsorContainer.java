package ph.devcon.android.sponsor.api;

import com.google.gson.annotations.Expose;

/**
 * Created by lope on 10/31/2014.
 */
public class SponsorContainer {
    @Expose
    private SponsorAPI sponsor;

    public SponsorAPI getSponsor() {
        return sponsor;
    }

    public void setSponsor(SponsorAPI sponsor) {
        this.sponsor = sponsor;
    }
}
