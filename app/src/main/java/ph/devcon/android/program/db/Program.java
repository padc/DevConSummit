package ph.devcon.android.program.db;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import ph.devcon.android.base.db.BaseDevCon;
import ph.devcon.android.program.api.ProgramAPI;
import ph.devcon.android.speaker.db.Speaker;

/**
 * Created by lope on 9/16/14.
 */
@DatabaseTable(daoClass = ProgramDaoImpl.class)
public class Program extends BaseDevCon {

    @DatabaseField
    String startAt;

    @DatabaseField
    String title;

    @DatabaseField
    String description;

    @DatabaseField
    String category;

    @ForeignCollectionField(eager = true)
    private ForeignCollection<Speaker> speakers;

    public static Program toProgram(ProgramAPI programAPIApi) {
        Program program = new Program();
        program.setCategory(programAPIApi.getCategory().getName());
        program.setDescription(programAPIApi.getDescription());
        program.setTitle(programAPIApi.getTitle());
        program.setStartAt(programAPIApi.getStartAt());
        return program;
    }

    public Speaker getMainSpeaker() {
        for (Speaker speaker : getSpeakers()) {
            return speaker;
        }
        return null;
    }

    public String getStartAt() {
        return startAt;
    }

    public void setStartAt(String startAt) {
        this.startAt = startAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ForeignCollection<Speaker> getSpeakers() {
        return speakers;
    }

    public void setSpeakers(ForeignCollection<Speaker> speakers) {
        this.speakers = speakers;
    }
}
