package ph.devcon.android.attendee.api;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lope on 10/31/2014.
 */
public class AttendeeBaseResponse {
    @Expose
    private List<AttendeeAPI> attendees = new ArrayList<AttendeeAPI>();

    public List<AttendeeAPI> getAttendees() {
        return attendees;
    }

    public void setAttendees(List<AttendeeAPI> attendees) {
        this.attendees = attendees;
    }
}
