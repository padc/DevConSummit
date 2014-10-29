package ph.devcon.android.program.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import ph.devcon.android.speaker.api.Speaker;

/**
 * Created by lope on 10/26/2014.
 */

public class ProgramAPI {
    @SerializedName("start_at")
    @Expose
    private String startAt;
    @Expose
    private String title;
    @Expose
    private String description;
    @Expose
    private Category category;
    @Expose
    private List<Speaker> speakers = new ArrayList<Speaker>();

    public String getStartAt() {
        return startAt;
    }

    public void setStartAt(String startAt) {
        this.startAt = startAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Speaker> getSpeakers() {
        return speakers;
    }

    public void setSpeakers(List<Speaker> speakers) {
        this.speakers = speakers;
    }

    public Speaker getMainSpeaker() {
        for (Speaker speaker : getSpeakers()) {
            return speaker;
        }
        return null;
    }

}
