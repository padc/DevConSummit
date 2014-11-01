package ph.devcon.android.category.api;

import com.google.gson.annotations.Expose;

/**
 * Created by lope on 10/29/14.
 */
public class CategoryAPI {

    @Expose
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}