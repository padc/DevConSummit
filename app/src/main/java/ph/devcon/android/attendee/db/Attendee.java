package ph.devcon.android.attendee.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import ph.devcon.android.attendee.api.AttendeeAPI;
import ph.devcon.android.base.db.BaseDevCon;
import ph.devcon.android.user.db.User;

/**
 * Created by lope on 10/9/14.
 */
@DatabaseTable(daoClass = AttendeeDaoImpl.class)
public class Attendee extends BaseDevCon {
    @DatabaseField(foreign = true, foreignAutoRefresh = true, foreignAutoCreate = true)
    User user;

    public static Attendee toAttendee(AttendeeAPI attendeeAPI) {
        Attendee attendee = new Attendee();
        User userDb = User.toUser(attendeeAPI.getUser());
        attendee.setUser(userDb);
        return attendee;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
