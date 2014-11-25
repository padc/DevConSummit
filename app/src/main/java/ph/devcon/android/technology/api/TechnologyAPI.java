package ph.devcon.android.technology.api;

import com.google.gson.annotations.Expose;

/**
 * Created by lope on 11/23/14.
 */
public class TechnologyAPI {

    @Expose
    private String name;
    @Expose
    private String slug;

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The slug
     */
    public String getSlug() {
        return slug;
    }

    /**
     * @param slug The slug
     */
    public void setSlug(String slug) {
        this.slug = slug;
    }

}