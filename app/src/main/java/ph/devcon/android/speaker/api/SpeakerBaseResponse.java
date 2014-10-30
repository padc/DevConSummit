package ph.devcon.android.speaker.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lope on 10/29/14.
 */
public class SpeakerBaseResponse {
    @Expose
    private List<SpeakerAPIContainer> speakers = new ArrayList<SpeakerAPIContainer>();

    @SerializedName("status_code")
    @Expose
    private Integer statusCode;

    public List<SpeakerAPIContainer> getSpeakers() {
        return speakers;
    }

    public void setSpeakers(List<SpeakerAPIContainer> speakers) {
        this.speakers = speakers;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

}
