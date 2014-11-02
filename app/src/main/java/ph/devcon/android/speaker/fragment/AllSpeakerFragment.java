package ph.devcon.android.speaker.fragment;

import android.os.Bundle;

import com.google.common.base.Optional;

import ph.devcon.android.speaker.event.FetchedAllSpeakerListEvent;

/**
 * Created by lope on 9/29/14.
 */
public class AllSpeakerFragment extends BaseSpeakerFragment {

    @Override
    public void onResume() {
        super.onResume();
        FetchedAllSpeakerListEvent event = eventBus.getStickyEvent(FetchedAllSpeakerListEvent.class);
        Optional<FetchedAllSpeakerListEvent> eventOptional = Optional.fromNullable(event);
        if (eventOptional.isPresent()) {
            setSpeakerList(eventOptional.get().speakers);
        }
    }

    @Override
    protected void executePopulateFromCache(Bundle savedInstanceState) {
        speakerService.getAll(getLoaderManager(), savedInstanceState);
    }

    @Override
    protected void executePopulateFromAPI() {
        speakerService.populateFromAPI();
    }

    @Override
    public boolean shouldDisplayTalkAsTitle() {
        return true;
    }

    public void onEventMainThread(FetchedAllSpeakerListEvent event) {
        setSpeakerList(event.speakers);
    }

}
