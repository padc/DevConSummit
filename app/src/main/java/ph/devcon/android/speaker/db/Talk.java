package ph.devcon.android.speaker.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import ph.devcon.android.base.db.BaseDevCon;
import ph.devcon.android.util.Util;

/**
 * Created by lope on 11/1/2014.
 */
@DatabaseTable(daoClass = TalkDaoImpl.class)
public class Talk extends BaseDevCon {
    @DatabaseField
    String name;

    @DatabaseField(foreign = true)
    Speaker speaker;

    public static Talk toTalk(String talkName) {
        Talk talk = null;
        if (!Util.isNullOrEmpty(talkName)) {
            talk = new Talk();
            talk.setName(talkName);
        }
        return talk;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Speaker getSpeaker() {
        return speaker;
    }

    public void setSpeaker(Speaker speaker) {
        this.speaker = speaker;
    }
}
