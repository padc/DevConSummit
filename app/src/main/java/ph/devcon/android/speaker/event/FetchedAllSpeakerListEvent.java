package ph.devcon.android.speaker.event;

import java.util.List;

import ph.devcon.android.speaker.db.Speaker;

/**
 * Created by lope on 10/29/14.
 */
public class FetchedAllSpeakerListEvent {
    public List<Speaker> speakers;

    public FetchedAllSpeakerListEvent(List<Speaker> speakers) {
        this.speakers = speakers;
    }
}
