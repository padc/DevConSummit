package ph.devcon.android.speaker.api;

import com.google.gson.annotations.Expose;

/**
 * Created by lope on 10/26/2014.
 */
public class Speaker {

    @Expose
    private String speakerName;
    @Expose
    private String speakerPosition;
    @Expose
    private String speakerCompany;

    public String getSpeakerName() {
        return speakerName;
    }

    public void setSpeakerName(String speakerName) {
        this.speakerName = speakerName;
    }

    public String getSpeakerPosition() {
        return speakerPosition;
    }

    public void setSpeakerPosition(String speakerPosition) {
        this.speakerPosition = speakerPosition;
    }

    public String getSpeakerCompany() {
        return speakerCompany;
    }

    public void setSpeakerCompany(String speakerCompany) {
        this.speakerCompany = speakerCompany;
    }

}
