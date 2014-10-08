package ph.devcon.android.news.db;

import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

import ph.devcon.android.base.db.BaseDevConDaoImpl;

/**
 * Created by lope on 10/6/14.
 */
public class NewsDaoImpl extends BaseDevConDaoImpl<News, Integer> implements NewsDao {
    public NewsDaoImpl(ConnectionSource connectionSource, Class<News> clazz) throws SQLException {
        super(connectionSource, clazz);
    }
}