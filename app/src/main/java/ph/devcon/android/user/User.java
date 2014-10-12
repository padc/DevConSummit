package ph.devcon.android.user;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import ph.devcon.android.attendee.db.SocialLink;
import ph.devcon.android.base.db.BaseDevCon;

/**
 * Created by lope on 9/13/14.
 */
@DatabaseTable(daoClass = UserDaoImpl.class)
public class User extends BaseDevCon {
    @DatabaseField
    String companyPosition;
    @DatabaseField
    String companyName;
    @DatabaseField
    String location;
    @DatabaseField
    String emailAddress;
    @DatabaseField
    String contactNumber;
    @DatabaseField
    String aboutMe;
    @DatabaseField
    String technology1;
    @DatabaseField
    String technology2;
    @DatabaseField
    String technology3;

    @ForeignCollectionField
    ForeignCollection<SocialLink> socialLinks;

    public String getCompanyPosition() {
        return companyPosition;
    }

    public void setCompanyPosition(String companyPosition) {
        this.companyPosition = companyPosition;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public String getTechnology1() {
        return technology1;
    }

    public void setTechnology1(String technology1) {
        this.technology1 = technology1;
    }

    public String getTechnology2() {
        return technology2;
    }

    public void setTechnology2(String technology2) {
        this.technology2 = technology2;
    }

    public String getTechnology3() {
        return technology3;
    }

    public void setTechnology3(String technology3) {
        this.technology3 = technology3;
    }

    public ForeignCollection<SocialLink> getSocialLinks() {
        return socialLinks;
    }

    public void setSocialLinks(ForeignCollection<SocialLink> socialLinks) {
        this.socialLinks = socialLinks;
    }
}
