package ph.devcon.android.attendee.event;

import java.util.List;

import ph.devcon.android.attendee.db.Attendee;

/**
 * Created by lope on 10/31/2014.
 */
public class FetchedAttendeeListEvent {
    List<Attendee> attendees;

    public FetchedAttendeeListEvent(List<Attendee> attendees) {
        this.attendees = attendees;
    }
}
