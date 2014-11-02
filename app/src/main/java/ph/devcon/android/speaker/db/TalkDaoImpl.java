package ph.devcon.android.speaker.db;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import ph.devcon.android.base.db.BaseDevConDaoImpl;
import ph.devcon.android.category.db.Category;

/**
 * Created by lope on 9/16/14.
 */
public class TalkDaoImpl extends BaseDevConDaoImpl<Talk, Integer> implements TalkDao {
    public TalkDaoImpl(ConnectionSource connectionSource, Class<Talk> clazz) throws SQLException {
        super(connectionSource, clazz);
    }

    @Override
    public boolean isCacheValid() throws SQLException {
        return queryForFirst(queryBuilder().prepare()) != null;
    }

    @Override
    public void clear() throws SQLException {
        TableUtils.clearTable(getConnectionSource(), Category.class);
    }
}
