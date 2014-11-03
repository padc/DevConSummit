package ph.devcon.android.sponsor.db;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import ph.devcon.android.base.db.BaseDevConDaoImpl;

/**
 * Created by lope on 9/16/14.
 */
public class SponsorDaoImpl extends BaseDevConDaoImpl<Sponsor, Integer> implements SponsorDao {
    public SponsorDaoImpl(ConnectionSource connectionSource, Class<Sponsor> clazz) throws SQLException {
        super(connectionSource, clazz);
    }

    @Override
    public void clear() throws SQLException {
        TableUtils.clearTable(getConnectionSource(), Sponsor.class);
    }

    @Override
    public boolean isCacheValid() throws SQLException {
        return queryForFirst(queryBuilder().prepare()) != null;
    }
}
