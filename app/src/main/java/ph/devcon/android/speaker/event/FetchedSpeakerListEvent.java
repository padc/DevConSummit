package ph.devcon.android.speaker.event;

import java.util.List;

import ph.devcon.android.speaker.db.Speaker;

/**
 * Created by lope on 10/29/14.
 */
public class FetchedSpeakerListEvent {
    public List<Speaker> speakers;

    public FetchedSpeakerListEvent(List<Speaker> speakers) {
        this.speakers = speakers;
    }
}
