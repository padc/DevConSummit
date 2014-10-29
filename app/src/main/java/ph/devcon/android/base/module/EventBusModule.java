package ph.devcon.android.base.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import de.greenrobot.event.EventBus;

/**
 * Created by lope on 10/29/14.
 */
@Module(library = true)
public class EventBusModule {
    @Provides
    @Singleton
    public EventBus provideEventBus() {
        return EventBus.getDefault();
    }
}