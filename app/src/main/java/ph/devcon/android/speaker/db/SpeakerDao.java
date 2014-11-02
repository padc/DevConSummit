package ph.devcon.android.speaker.db;

import com.j256.ormlite.stmt.PreparedQuery;

import java.sql.SQLException;

import ph.devcon.android.base.db.BaseDevConDao;

/**
 * Created by lope on 9/16/14.
 */
public interface SpeakerDao extends BaseDevConDao<Speaker, Integer> {
    public PreparedQuery<Speaker> getAll() throws SQLException;

    public PreparedQuery<Speaker> getSpeakers() throws SQLException;

    public PreparedQuery<Speaker> getPanels() throws SQLException;
}
