package ph.devcon.android.attendee.db;

import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

import ph.devcon.android.base.db.BaseDevConDaoImpl;
import ph.devcon.android.speaker.db.Speaker;

/**
 * Created by lope on 9/16/14.
 */
public class AttendeeDaoImpl extends BaseDevConDaoImpl<Speaker, Integer> implements AttendeeDao {
    public AttendeeDaoImpl(ConnectionSource connectionSource, Class<Speaker> clazz) throws SQLException {
        super(connectionSource, clazz);
    }

    @Override
    public void clear() throws SQLException {
        // TODO
    }

    @Override
    public boolean isCacheValid() throws SQLException {
        // TODO
        return false;
    }
}
