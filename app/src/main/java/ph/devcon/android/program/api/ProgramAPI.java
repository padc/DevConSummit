package ph.devcon.android.program.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import ph.devcon.android.category.api.CategoryAPI;
import ph.devcon.android.speaker.api.SpeakerAPI;

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
    private CategoryAPI category;
    @Expose
    private List<SpeakerAPI> speakers = new ArrayList<SpeakerAPI>();

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

    public CategoryAPI getCategory() {
        return category;
    }

    public void setCategory(CategoryAPI category) {
        this.category = category;
    }

    public List<SpeakerAPI> getSpeakers() {
        return speakers;
    }

    public void setSpeakers(List<SpeakerAPI> speakers) {
        this.speakers = speakers;
    }

    public SpeakerAPI getMainSpeaker() {
        for (SpeakerAPI speaker : getSpeakers()) {
            return speaker;
        }
        return null;
    }

}
