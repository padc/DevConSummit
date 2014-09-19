package ph.devcon.android.program.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

import ph.devcon.android.base.db.BaseDevCon;
import ph.devcon.android.speaker.db.Speaker;

/**
 * Created by lope on 9/16/14.
 */
@DatabaseTable(daoClass = ProgramDaoImpl.class)
public class Program extends BaseDevCon {
    @DatabaseField
    Date time;
    @DatabaseField(index = true)
    String title;
    @DatabaseField(foreign = true)
    Speaker speaker;

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Speaker getSpeaker() {
        return speaker;
    }

    public void setSpeaker(Speaker speaker) {
        this.speaker = speaker;
    }
}
