package ph.devcon.android.attendee.service;

import java.util.List;

import ph.devcon.android.attendee.api.AttendeeBaseResponse;
import ph.devcon.android.attendee.db.Attendee;
import ph.devcon.android.base.service.BaseAPICacheService;

/**
 * Created by lope on 10/29/14.
 */
public interface AttendeeService extends BaseAPICacheService<List<Attendee>, AttendeeBaseResponse> {
    public Attendee getAttendee(int id);
}
