package ph.devcon.android.speaker.db;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import ph.devcon.android.base.db.BaseDevConDaoImpl;

/**
 * Created by lope on 9/16/14.
 */
public class SpeakerDaoImpl extends BaseDevConDaoImpl<Speaker, Integer> implements SpeakerDao {
    public SpeakerDaoImpl(ConnectionSource connectionSource, Class<Speaker> clazz) throws SQLException {
        super(connectionSource, clazz);
    }

    @Override
    public void clear() throws SQLException {
        TableUtils.clearTable(getConnectionSource(), Speaker.class);
    }

    @Override
    public boolean isCacheValid() throws SQLException {
        return queryForFirst(queryBuilder().prepare()) != null;
    }
}
