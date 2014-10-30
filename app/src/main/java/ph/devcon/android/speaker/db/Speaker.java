package ph.devcon.android.speaker.db;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.common.base.Optional;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import ph.devcon.android.base.db.BaseDevCon;
import ph.devcon.android.program.db.Program;
import ph.devcon.android.speaker.api.SpeakerAPI;
import ph.devcon.android.util.Util;

/**
 * Created by lope on 9/16/14.
 */
@DatabaseTable(daoClass = SpeakerDaoImpl.class)
public class Speaker extends BaseDevCon {

    @DatabaseField(index = true)
    String firstName;

    @DatabaseField(index = true)
    String lastName;

    @DatabaseField
    String position;

    @DatabaseField
    String company;

    @DatabaseField
    String description;

    @DatabaseField
    String twitterHandle;

    @DatabaseField
    String website;

    @DatabaseField
    String photoUrl;

    @DatabaseField(dataType = DataType.BYTE_ARRAY)
    byte[] speakerIcon;

    @DatabaseField
    String talkTitle;

    @DatabaseField
    String panelTitle;

    @DatabaseField(foreign = true)
    Program program;

    public Bitmap getSpeakerIconBitmap() {
        return BitmapFactory.decodeByteArray(speakerIcon, 0, speakerIcon.length);
    }

    public static Speaker toSpeaker(SpeakerAPI speakerAPI) {
        Speaker speaker = new Speaker();
        speaker.setFirstName(speakerAPI.getFirstName());
        speaker.setLastName(speakerAPI.getLastName());
        speaker.setPosition(speakerAPI.getPosition());
        speaker.setCompany(speakerAPI.getCompany());
        speaker.setDescription(speakerAPI.getDescription());
        speaker.setTwitterHandle(speakerAPI.getTwitterHandle());
        speaker.setWebsite(speakerAPI.getWebsite());
        speaker.setPhotoUrl(speakerAPI.getPhotoUrl());
        int counter = 0;
        for (String title : speakerAPI.getTalk()) {
            if (counter == 0) {
                speaker.setTalkTitle(title);
            } else if (title.startsWith("PANEL:")) {
                speaker.setPanelTitle(title);
            }
            counter++;
        }
        return speaker;
    }

    public String getMainTalkTitle() {
        String returnTitle = "";
        Optional<Program> programOptional = Optional.fromNullable(program);
        if (programOptional.isPresent()) {
            Program programMain = programOptional.get();
            String programTitle = programMain.getTitle();
            if (Util.isNullOrEmpty(programTitle)) {
                returnTitle = programTitle;
            }
        } else {
            returnTitle = getTalkTitle();
        }
        return returnTitle;
    }

    public String getFullName() {
        return getFirstName() + " " + getLastName();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTwitterHandle() {
        return twitterHandle;
    }

    public void setTwitterHandle(String twitterHandle) {
        this.twitterHandle = twitterHandle;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public byte[] getSpeakerIcon() {
        return speakerIcon;
    }

    public void setSpeakerIcon(byte[] speakerIcon) {
        this.speakerIcon = speakerIcon;
    }

    public String getTalkTitle() {
        return talkTitle;
    }

    public void setTalkTitle(String talkTitle) {
        this.talkTitle = talkTitle;
    }

    public String getPanelTitle() {
        return panelTitle;
    }

    public void setPanelTitle(String panelTitle) {
        this.panelTitle = panelTitle;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }
}
