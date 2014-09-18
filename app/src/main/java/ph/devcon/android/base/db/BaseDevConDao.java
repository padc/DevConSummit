package ph.devcon.android.base.db;

import com.j256.ormlite.dao.Dao;

/**
 * Created by lope on 9/17/14.
 */
public abstract interface BaseDevConDao<T extends BaseDevCon, ID> extends Dao<T, ID> {
}
