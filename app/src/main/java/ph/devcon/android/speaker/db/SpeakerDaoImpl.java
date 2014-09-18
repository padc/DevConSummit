package ph.devcon.android.speaker.db;

import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

import ph.devcon.android.base.db.BaseDevConDaoImpl;
import ph.devcon.android.program.db.Program;
import ph.devcon.android.program.db.ProgramDao;

/**
 * Created by lope on 9/16/14.
 */
public class SpeakerDaoImpl extends BaseDevConDaoImpl<Program, Integer> implements ProgramDao {
    public SpeakerDaoImpl(ConnectionSource connectionSource, Class<Program> clazz) throws SQLException {
        super(connectionSource, clazz);
    }
}
