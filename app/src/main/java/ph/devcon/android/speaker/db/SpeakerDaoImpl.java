package ph.devcon.android.speaker.db;

import com.j256.ormlite.stmt.PreparedQuery;
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
        return queryBuilder().where().isNotNull("talkTitle").countOf() > 0;
    }

    @Override
    public PreparedQuery<Speaker> getAll() throws SQLException {
        return queryBuilder().prepare();
    }

    @Override
    public PreparedQuery<Speaker> getSpeakers() throws SQLException {
        return queryBuilder().where().eq("isSpeaker", true).prepare();
    }

    @Override
    public PreparedQuery<Speaker> getPanels() throws SQLException {
        return queryBuilder().where().eq("isPanel", true).prepare();
    }
}
