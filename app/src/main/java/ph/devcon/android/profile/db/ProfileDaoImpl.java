package ph.devcon.android.profile.db;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import ph.devcon.android.base.db.BaseDevConDaoImpl;
import ph.devcon.android.program.db.Program;

/**
 * Created by lope on 11/1/2014.
 */
public class ProfileDaoImpl extends BaseDevConDaoImpl<Profile, Integer> implements ProfileDao {
    public ProfileDaoImpl(ConnectionSource connectionSource, Class<Profile> clazz) throws SQLException {
        super(connectionSource, clazz);
    }

    @Override
    public boolean isCacheValid() throws SQLException {
        return queryForFirst(queryBuilder().prepare()) != null;
    }

    @Override
    public void clear() throws SQLException {
        TableUtils.clearTable(getConnectionSource(), Program.class);
    }
}
