package ph.devcon.android.speaker.service;

import java.util.List;

import ph.devcon.android.speaker.db.Speaker;

/**
 * Created by lope on 10/6/14.
 */
public interface SpeakerService {
    public List<Speaker> getAll();

    public List<Speaker> getSpeakers();

    public List<Speaker> getPanels();
}
