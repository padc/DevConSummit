/*
 * Copyright (C) 2014 Philippine Android Developers Community
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ph.devcon.android.user.db;

import com.google.common.base.Optional;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ph.devcon.android.base.db.BaseDevCon;
import ph.devcon.android.technology.db.Technology;
import ph.devcon.android.user.api.UserAPI;
import ph.devcon.android.util.Util;

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
    private String contactNumber;

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

    @DatabaseField(dataType = DataType.BYTE_ARRAY)
    private byte[] photoImage;

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

    public String getFullName() {
        return getFirstName() + " " + getLastName();
    }

    public String getPositionAndCompany() {
        return getPosition() + " at " + getCompany();
    }

    public String getEmailAndContact() {
        // TODO contact number
        return getEmail() + " · ";
    }

    public String getAboutTitle() {
        return "About " + getFirstName();
    }

    public String getMainTechnologyTitle() {
        Technology primaryTechnology = getPrimaryTechnology();
        Optional<Technology> technologyOptional = Optional.fromNullable(primaryTechnology);
        String mainTech = "";
        if (technologyOptional.isPresent())
            mainTech = technologyOptional.get().getTitle();
        return mainTech;
    }

    public List<String> getOtherTechnologiesTitleList() {
        List<String> techTitles = new ArrayList<String>();
        Optional<ForeignCollection<Technology>> technologiesOptional = Optional.fromNullable(getTechnologies());
        if (technologiesOptional.isPresent()) {
            for (Technology technology : getTechnologies()) {
                techTitles.add(technology.getTitle());
            }
        }
        return techTitles;
    }

    public String getPrettyTechnologyList() {
        StringBuilder sb = new StringBuilder();
        Technology primaryTechnology = getPrimaryTechnology();
        Optional<Technology> technologyOptional = Optional.fromNullable(primaryTechnology);
        String mainTech = "";
        if (technologyOptional.isPresent())
            mainTech = technologyOptional.get().getTitle();
        if (!Util.isNullOrEmpty(mainTech)) {
            sb.append(mainTech);
        }
        if (Optional.fromNullable(getTechnologies()).isPresent())
            for (Technology technology : getTechnologies()) {
                if (!technology.getTitle().equals(mainTech)) {
                    sb.append(technology.getTitle());
                    sb.append(" ");
                }
            }
        return sb.toString();
    }

    public String getPrettyMainTechnology() {
        if (!Util.isNullOrEmpty(getPrettyMainTechnology())) {
            return getPrettyTechnologyList() + " (Primary)";
        }
        return "";
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

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
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

    public byte[] getPhotoImage() {
        return photoImage;
    }

    public void setPhotoImage(byte[] photoImage) {
        this.photoImage = photoImage;
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
