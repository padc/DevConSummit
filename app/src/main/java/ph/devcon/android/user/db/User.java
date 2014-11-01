package ph.devcon.android.user.db;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import ph.devcon.android.base.db.BaseDevCon;
import ph.devcon.android.technology.db.Technology;
import ph.devcon.android.user.api.UserAPI;

/**
 * Created by lope on 9/13/14.
 */
@DatabaseTable(daoClass = UserDaoImpl.class)
public class User extends BaseDevCon {

    @DatabaseField
    private String email;

    @DatabaseField
    private String firstName;

    @DatabaseField
    private String lastName;

    @DatabaseField
    private String position;

    @DatabaseField
    private String company;

    @DatabaseField
    private String location;

    @DatabaseField
    private String description;

    @DatabaseField
    private String website;

    @DatabaseField
    private String facebookUrl;

    @DatabaseField
    private String twitterHandle;

    @DatabaseField
    private String photoUrl;

    @DatabaseField(foreign = true)
    private Technology primaryTechnology;

    @ForeignCollectionField
    private ForeignCollection<Technology> technologies;

    public static User toUser(UserAPI userAPI) {
        User user = new User();
        user.setEmail(userAPI.getEmail());
        user.setFirstName(userAPI.getFirstName());
        user.setLastName(userAPI.getLastName());
        user.setPosition(userAPI.getPosition());
        user.setCompany(userAPI.getCompany());
        user.setLocation(userAPI.getLocation());
        user.setDescription(userAPI.getDescription());
        user.setWebsite(userAPI.getWebsite());
        user.setFacebookUrl(userAPI.getFacebookUrl());
        user.setTwitterHandle(userAPI.getTwitterHandle());
        user.setPhotoUrl(userAPI.getPhotoUrl());
        return user;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getFacebookUrl() {
        return facebookUrl;
    }

    public void setFacebookUrl(String facebookUrl) {
        this.facebookUrl = facebookUrl;
    }

    public String getTwitterHandle() {
        return twitterHandle;
    }

    public void setTwitterHandle(String twitterHandle) {
        this.twitterHandle = twitterHandle;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public Technology getPrimaryTechnology() {
        return primaryTechnology;
    }

    public void setPrimaryTechnology(Technology primaryTechnology) {
        this.primaryTechnology = primaryTechnology;
    }

    public ForeignCollection<Technology> getTechnologies() {
        return technologies;
    }

    public void setTechnologies(ForeignCollection<Technology> technologies) {
        this.technologies = technologies;
    }
}
