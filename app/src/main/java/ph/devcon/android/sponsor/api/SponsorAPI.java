package ph.devcon.android.sponsor.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by lope on 10/31/2014.
 */
public class SponsorAPI {

    @Expose
    private String name;

    @SerializedName("photo_url")
    @Expose
    private String photoUrl;

    @Expose
    private String website;

    @Expose
    private SponsorTypeAPI category;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public SponsorTypeAPI getCategory() {
        return category;
    }

    public void setCategory(SponsorTypeAPI category) {
        this.category = category;
    }
}
