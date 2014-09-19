package ph.devcon.android.speaker.db;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import ph.devcon.android.base.db.BaseDevCon;
import ph.devcon.android.program.db.Program;

/**
 * Created by lope on 9/16/14.
 */
@DatabaseTable(daoClass = SpeakerDaoImpl.class)
public class Speaker extends BaseDevCon {
    @DatabaseField(index = true)
    String name;
    @DatabaseField(foreign = true)
    ForeignCollection<Program> programs;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ForeignCollection<Program> getPrograms() {
        return programs;
    }

    public void setPrograms(ForeignCollection<Program> programs) {
        this.programs = programs;
    }
}
