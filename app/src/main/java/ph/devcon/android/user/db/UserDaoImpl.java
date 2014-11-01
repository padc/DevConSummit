package ph.devcon.android.user.db;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import ph.devcon.android.base.db.BaseDevConDaoImpl;
import ph.devcon.android.program.db.Program;

/**
 * Created by lope on 9/16/14.
 */
public class UserDaoImpl extends BaseDevConDaoImpl<User, Integer> implements UserDao {
    public UserDaoImpl(ConnectionSource connectionSource, Class<User> clazz) throws SQLException {
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
