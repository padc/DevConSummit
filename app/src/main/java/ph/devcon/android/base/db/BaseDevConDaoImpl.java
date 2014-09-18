package ph.devcon.android.base.db;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.Date;

/**
 * Created by lope on 9/17/14.
 */
public abstract class BaseDevConDaoImpl<T extends BaseDevCon, ID> extends BaseDaoImpl<T, ID> implements BaseDevConDao<T, ID> {

    public BaseDevConDaoImpl(ConnectionSource connectionSource, Class<T> clazz)
            throws SQLException {
        super(connectionSource, clazz);
    }

    @Override
    public int create(T baseDevCon) throws SQLException {
        baseDevCon.setDateCreated(new Date());
        int count = super.create(baseDevCon);
        return count;
    }

    @Override
    public int update(T baseDevCon) throws SQLException {
        baseDevCon.setDateUpdated(new Date());
        int count = super.update(baseDevCon);
        return count;
    }

}