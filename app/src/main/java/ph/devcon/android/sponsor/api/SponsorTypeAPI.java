package ph.devcon.android.sponsor.api;

import com.google.gson.annotations.Expose;

/**
 * Created by lope on 11/3/14.
 */
public class SponsorTypeAPI {

    @Expose
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
