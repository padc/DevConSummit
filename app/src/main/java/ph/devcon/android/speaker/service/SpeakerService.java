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

    public void getAll(android.support.v4.app.LoaderManager loaderManager, Bundle savedInstanceState);

    public void getSpeakers(android.support.v4.app.LoaderManager loaderManager, Bundle savedInstanceState);

    public void getPanels(android.support.v4.app.LoaderManager loaderManager, Bundle savedInstanceState);

    public void populateFromCache(android.support.v4.app.LoaderManager loaderManager, Bundle savedInstanceState);

    public Speaker getSpeaker(int id);
}
