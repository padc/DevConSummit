package ph.devcon.android.program.api;

import com.google.gson.annotations.Expose;

/**
 * Created by lope on 10/29/14.
 */
public class Category {

    @Expose
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}