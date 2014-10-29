package ph.devcon.android.program.db;

import java.sql.SQLException;

import ph.devcon.android.base.db.BaseDevConDao;

/**
 * Created by lope on 9/16/14.
 */
public interface ProgramDao extends BaseDevConDao<Program, Integer> {
    public boolean isCacheValid() throws SQLException;

    public void clear() throws SQLException;
}
