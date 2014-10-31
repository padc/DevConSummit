package ph.devcon.android.user.db;

import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

import ph.devcon.android.base.db.BaseDevConDaoImpl;

/**
 * Created by lope on 9/16/14.
 */
public class UserDaoImpl extends BaseDevConDaoImpl<User, Integer> implements UserDao {
    public UserDaoImpl(ConnectionSource connectionSource, Class<User> clazz) throws SQLException {
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
