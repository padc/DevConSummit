package ph.devcon.android.news.db;

import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

import ph.devcon.android.base.db.BaseDevConDaoImpl;

/**
 * Created by lope on 10/6/14.
 */
public class TagDaoImpl extends BaseDevConDaoImpl<Tag, Integer> implements TagDao {
    public TagDaoImpl(ConnectionSource connectionSource, Class<Tag> clazz) throws SQLException {
        super(connectionSource, clazz);
    }
}
