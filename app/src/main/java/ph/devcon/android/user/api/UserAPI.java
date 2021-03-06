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

package ph.devcon.android.user.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import ph.devcon.android.technology.api.TechnologyAPI;

/**
 * Created by lope on 10/31/2014.
 */
public class UserAPI {

    @Expose
    private String email;

    @SerializedName("first_name")
    @Expose
    private String firstName;

    @SerializedName("last_name")
    @Expose
    private String lastName;

    @Expose
    private String position;

    @Expose
    private String company;

    @Expose
    private String location;

    @Expose
    private String description;

    @Expose
    private String website;

    @SerializedName("facebook_url")
    @Expose
    private String facebookUrl;

    @SerializedName("twitter_handle")
    @Expose
    private String twitterHandle;

    @SerializedName("photo_url")
    @Expose
    private String photoUrl;

    @SerializedName("primary_technology")
    @Expose
    private TechnologyAPI primaryTechnology;

    @Expose
    private List<TechnologyAPI> technologies = new ArrayList<TechnologyAPI>();

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

    public Object getPrimaryTechnology() {
        return primaryTechnology;
    }

    public void setPrimaryTechnology(TechnologyAPI primaryTechnology) {
        this.primaryTechnology = primaryTechnology;
    }

    public List<TechnologyAPI> getTechnologies() {
        return technologies;
    }

    public void setTechnologies(List<TechnologyAPI> technologies) {
        this.technologies = technologies;
    }
}
