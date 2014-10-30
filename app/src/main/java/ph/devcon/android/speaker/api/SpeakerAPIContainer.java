package ph.devcon.android.speaker.api;

import com.google.gson.annotations.Expose;

/**
 * Created by lope on 10/29/14.
 */
public class SpeakerAPIContainer {

    @Expose
    private SpeakerAPI speaker;

    public SpeakerAPI getSpeaker() {
        return speaker;
    }

    public void setSpeaker(SpeakerAPI speaker) {
        this.speaker = speaker;
    }

}
