package ph.devcon.android.attendee.db;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import ph.devcon.android.base.db.BaseDevConDaoImpl;

/**
 * Created by lope on 9/16/14.
 */
public class AttendeeDaoImpl extends BaseDevConDaoImpl<Attendee, Integer> implements AttendeeDao {
    public AttendeeDaoImpl(ConnectionSource connectionSource, Class<Attendee> clazz) throws SQLException {
        super(connectionSource, clazz);
    }

    @Override
    public boolean isCacheValid() throws SQLException {
        return queryForFirst(queryBuilder().prepare()) != null;
    }

    @Override
    public void clear() throws SQLException {
        TableUtils.clearTable(getConnectionSource(), Attendee.class);
    }

}
