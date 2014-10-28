package ph.devcon.android.program.db;

import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

import ph.devcon.android.base.db.BaseDevConDaoImpl;

/**
 * Created by lope on 9/16/14.
 */
public class ProgramDaoImpl extends BaseDevConDaoImpl<Program, Integer> implements ProgramDao {
    public ProgramDaoImpl(ConnectionSource connectionSource, Class<Program> clazz) throws SQLException {
        super(connectionSource, clazz);
    }

    @Override
    public boolean isCacheValid() {
        return true;
    }
}
