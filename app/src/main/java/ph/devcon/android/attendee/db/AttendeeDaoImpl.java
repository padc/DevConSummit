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

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import ph.devcon.android.base.db.BaseDevConDaoImpl;
import ph.devcon.android.util.Util;

/**
 * Created by lope on 9/16/14.
 */
public class AttendeeDaoImpl extends BaseDevConDaoImpl<Attendee, Integer> implements AttendeeDao {
    FTSAttendee ftsAttendee;

    public AttendeeDaoImpl(ConnectionSource connectionSource, Class<Attendee> clazz) throws SQLException {
        super(connectionSource, clazz);
    }

    @Override
    public void setFTSAttendee(FTSAttendee ftsAttendee) {
        this.ftsAttendee = ftsAttendee;
    }

    @Override
    public int create(Attendee attendee) throws SQLException {
        ftsAttendee.create(attendee);
        return super.create(attendee);
    }

    @Override
    public boolean isCacheValid() throws SQLException {
        return queryForFirst(queryBuilder().prepare()) != null;
    }

    @Override
    public void clear() throws SQLException {
        TableUtils.clearTable(getConnectionSource(), Attendee.class);
        Util.clearTable(getConnectionSource(), FTSAttendee.TABLE_ATTENDEE_FTS);
    }

}
