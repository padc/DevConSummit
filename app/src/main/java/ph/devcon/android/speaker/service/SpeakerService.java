package ph.devcon.android.speaker.service;

import android.os.Bundle;

import java.util.List;

import ph.devcon.android.base.service.BaseAPICacheService;
import ph.devcon.android.speaker.api.SpeakerBaseResponse;
import ph.devcon.android.speaker.db.Speaker;

/**
 * Created by lope on 10/6/14.
 */
public interface SpeakerService extends BaseAPICacheService<List<Speaker>, SpeakerBaseResponse> {

    public List<Speaker> getAll();

    public List<Speaker> getSpeakers();

    public List<Speaker> getPanels();

    public void populateFromCache(android.support.v4.app.LoaderManager loaderManager, Bundle savedInstanceState);

}
