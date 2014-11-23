/*
 * Copyright (C) 2014 Philippine Android Developers Community
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Attendee other = (Attendee) obj;
        return com.google.common.base.Objects.equal(this.getId(), other.getId());
    }

    @Override
    public int hashCode() {
        return com.google.common.base.Objects.hashCode(this.getId(), this.getUser().getFirstName(),
                this.getUser().getLastName(), null);
    }
}
