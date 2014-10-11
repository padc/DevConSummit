package ph.devcon.android.attendee.db;

import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

import ph.devcon.android.base.db.BaseDevConDaoImpl;

/**
 * Created by lope on 10/12/14.
 */
public class TechnologyDaoImpl extends BaseDevConDaoImpl<Technology, Integer> implements TechnologyDao {
    public TechnologyDaoImpl(ConnectionSource connectionSource, Class<Technology> clazz) throws SQLException {
        super(connectionSource, clazz);
    }
}
