package ph.devcon.android.speaker.db;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DataType;
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
    @DatabaseField(dataType = DataType.BYTE_ARRAY)
    byte[] speakerIcon;
    @DatabaseField(foreign = true)
    ForeignCollection<Program> programs;

    public Bitmap getSpeakerIconBitmap() {
        return BitmapFactory.decodeByteArray(speakerIcon, 0, speakerIcon.length);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getSpeakerIcon() {
        return speakerIcon;
    }

    public void setSpeakerIcon(byte[] speakerIcon) {
        this.speakerIcon = speakerIcon;
    }

    public ForeignCollection<Program> getPrograms() {
        return programs;
    }

    public void setPrograms(ForeignCollection<Program> programs) {
        this.programs = programs;
    }
}
