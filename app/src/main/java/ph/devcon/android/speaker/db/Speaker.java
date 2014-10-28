package ph.devcon.android.speaker.db;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
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

    @SerializedName("speakerName")
    @Expose
    @DatabaseField(index = true)
    String name;

    @DatabaseField(index = true)
    String title;

    @SerializedName("speakerPosition")
    @Expose
    @DatabaseField
    String position;

    @SerializedName("speakerCompany")
    @Expose
    @DatabaseField
    String company;

    @DatabaseField(dataType = DataType.BYTE_ARRAY)
    byte[] speakerIcon;

    @DatabaseField(foreign = true)
    Program program;

    public Bitmap getSpeakerIconBitmap() {
        return BitmapFactory.decodeByteArray(speakerIcon, 0, speakerIcon.length);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public byte[] getSpeakerIcon() {
        return speakerIcon;
    }

    public void setSpeakerIcon(byte[] speakerIcon) {
        this.speakerIcon = speakerIcon;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }
}
