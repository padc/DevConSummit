package ph.devcon.android.sponsor.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lope on 10/31/2014.
 */
public class SponsorBaseResponse {

    @Expose
    private List<SponsorContainer> sponsors = new ArrayList<SponsorContainer>();

    @SerializedName("status_code")
    @Expose
    private Integer statusCode;

    public List<SponsorContainer> getSponsors() {
        return sponsors;
    }

    public void setSponsors(List<SponsorContainer> sponsors) {
        this.sponsors = sponsors;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

}
