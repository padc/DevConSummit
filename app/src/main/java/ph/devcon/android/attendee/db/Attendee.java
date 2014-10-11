package ph.devcon.android.attendee.db;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import ph.devcon.android.base.db.BaseDevCon;

/**
 * Created by lope on 10/9/14.
 */
@DatabaseTable(daoClass = AttendeeDaoImpl.class)
public class Attendee extends BaseDevCon {

    @DatabaseField(index = true)
    String name;

    @DatabaseField
    String title;

    @DatabaseField
    String company;

    @DatabaseField
    String location;

    @DatabaseField
    String emailAddress;

    @DatabaseField
    String phoneNumber;

    @DatabaseField
    String about;

    @DatabaseField
    byte[] image;

    @DatabaseField
    byte[] imagePreview;

    @ForeignCollectionField
    ForeignCollection<Technology> technologies;

    @ForeignCollectionField
    ForeignCollection<SocialLink> socialLinks;

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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public byte[] getImagePreview() {
        return imagePreview;
    }

    public void setImagePreview(byte[] imagePreview) {
        this.imagePreview = imagePreview;
    }

    public ForeignCollection<Technology> getTechnologies() {
        return technologies;
    }

    public void setTechnologies(ForeignCollection<Technology> technologies) {
        this.technologies = technologies;
    }

    public ForeignCollection<SocialLink> getSocialLinks() {
        return socialLinks;
    }

    public void setSocialLinks(ForeignCollection<SocialLink> socialLinks) {
        this.socialLinks = socialLinks;
    }
}
