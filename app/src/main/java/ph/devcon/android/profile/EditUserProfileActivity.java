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

package ph.devcon.android.profile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.base.Optional;
import com.j256.ormlite.dao.ForeignCollection;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.sql.SQLException;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import ph.devcon.android.DevConApplication;
import ph.devcon.android.R;
import ph.devcon.android.auth.AuthService;
import ph.devcon.android.base.event.NetworkUnavailableEvent;
import ph.devcon.android.login.LoginActivity;
import ph.devcon.android.profile.db.Profile;
import ph.devcon.android.profile.event.FetchedProfileEvent;
import ph.devcon.android.profile.event.UpdatedProfileEvent;
import ph.devcon.android.profile.event.UpdatedProfileFailedEvent;
import ph.devcon.android.profile.service.ProfileService;
import ph.devcon.android.technology.db.Technology;
import ph.devcon.android.user.db.User;
import ph.devcon.android.user.db.UserDao;
import ph.devcon.android.util.Util;

/**
 * Created by lope on 9/13/14.
 */
public class EditUserProfileActivity extends ActionBarActivity implements SwipeRefreshLayout.OnRefreshListener {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    @Inject
    EventBus eventBus;
    @Inject
    ProfileService profileService;
    @InjectView(R.id.img_user)
    ImageView imgUser;
    @InjectView(R.id.txt_full_name)
    TextView txtFullName;
    @InjectView(R.id.edt_company_position)
    EditText edtCompanyPosition;
    @InjectView(R.id.edt_company_name)
    EditText edtCompanyName;
    @InjectView(R.id.edt_location)
    EditText edtLocation;
    @InjectView(R.id.edt_email_address)
    EditText edtEmailAddress;
    @InjectView(R.id.edt_about_me)
    EditText edtAboutMe;
    @InjectView(R.id.edt_tech_1)
    EditText edtTech1;
    @InjectView(R.id.edt_tech_2)
    EditText edtTech2;
    @InjectView(R.id.edt_tech_3)
    EditText edtTech3;
    @InjectView(R.id.edt_domain_name)
    EditText edtDomainName;
    @InjectView(R.id.edt_twitter)
    EditText edtTwitter;
    @InjectView(R.id.edt_facebook)
    EditText edtFacebook;
    @InjectView(R.id.cont_edit_profile)
    SwipeRefreshLayout swipeLayout;

    // submission
    @InjectView(R.id.cont_submit)
    ViewGroup contSubmit;
    @InjectView(R.id.img_save_changes)
    ImageView imgSaveChanges;
    @InjectView(R.id.txt_save_changes)
    TextView txtSaveChanges;
    Profile profile;
    @Inject
    UserDao userDao;
    @Inject
    AuthService authService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_edit_profile);
        setTitle(getString(R.string.edit_profile));
        DevConApplication.injectMembers(this);
        ButterKnife.inject(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        initSwipeLayout();
        if (!eventBus.isRegistered(this)) {
            eventBus.register(this);
        }
        if (profileService.isCacheValid()) {
            profileService.populateFromCache(getLoaderManager(), savedInstanceState);
        } else {
            profileService.populateFromAPI();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        // Only show items in the action bar relevant to this screen
        // if the drawer is not showing. Otherwise, let the drawer
        // decide what to show in the action bar.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case R.id.action_signout:
                authService.logout();
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void initSwipeLayout() {
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorSchemeResources(R.color.yellow,
                R.color.orange,
                R.color.purple,
                R.color.blue);
        swipeLayout.setRefreshing(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        eventBus.removeStickyEvent(LaunchCameraEvent.class);
        eventBus.unregister(this);
    }

    public void setProfile(Profile profile) {
        Optional<Profile> profileOptional = Optional.of(profile);
        if (profileOptional.isPresent()) {
            if (Optional.of(profile.getUser()).isPresent()) {
                this.profile = profile;
                User user = profile.getUser();
                if (!Util.isNullOrEmpty(user.getPhotoUrl()))
                    Picasso.with(this).load(user.getPhotoUrl()).placeholder(R.drawable.ic_summit_logo).into(imgUser);
                txtFullName.setText(user.getFullName());
                edtCompanyPosition.setText(user.getPosition());
                edtCompanyName.setText(user.getCompany());
                edtLocation.setText(user.getLocation());
                edtEmailAddress.setText(user.getEmail());
                edtAboutMe.setText(user.getDescription());
                edtTech1.setText(user.getMainTechnologyTitle());
                int counter = 0;
                for (String title : user.getOtherTechnologiesTitleList()) {
                    if (counter == 0)
                        edtTech2.setText(title);
                    else if (counter == 1)
                        edtTech3.setText(title);
                    counter++;
                }
                edtDomainName.setText(user.getWebsiteDomain());
                edtTwitter.setText(user.getTwitterHandle());
                edtFacebook.setText(user.getFacebookHandle());
            }
        }
    }

    @OnClick(R.id.cont_submit)
    public void onClickSaveChanges(View view) {
        Optional<Profile> profileOptional = Optional.of(profile);
        if (profileOptional.isPresent()) {
            User user = profile.getUser();
            user.setPosition(edtCompanyPosition.getText().toString());
            user.setCompany(edtCompanyName.getText().toString());
            user.setLocation(edtLocation.getText().toString());
            user.setEmail(edtEmailAddress.getText().toString());
            user.setDescription(edtAboutMe.getText().toString());
            user.setWebsite(edtDomainName.getText().toString());
            user.setFacebookUrl(edtFacebook.getText().toString());
            user.setTwitterHandle(edtTwitter.getText().toString());
            String primaryTech = edtTech1.getText().toString();
            user.setPrimaryTechnology(Technology.toTechnology(user, primaryTech));
            try {
                ForeignCollection<Technology> technologies =
                        userDao.getEmptyForeignCollection("technologies");
                String tech2 = edtTech2.getText().toString();
                technologies.add(Technology.toTechnology(user, tech2));
                String tech3 = edtTech3.getText().toString();
                technologies.add(Technology.toTechnology(user, tech3));
                user.setTechnologies(technologies);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            profileService.updateAPI(profile);
            disableSubmitButton();
        }
    }

    protected void enableSubmitButton() {
        txtSaveChanges.setText(getString(R.string.save_changes));
        swipeLayout.setRefreshing(false);
        contSubmit.setEnabled(true);
        contSubmit.setBackgroundResource(R.drawable.selectable_green);
        imgSaveChanges.setVisibility(View.VISIBLE);

    }

    protected void disableSubmitButton() {
        txtSaveChanges.setText(getString(R.string.save_changes));
        swipeLayout.setRefreshing(true);
        contSubmit.setEnabled(false);
        contSubmit.setBackgroundResource(R.drawable.selectable_green_inverse);
        txtSaveChanges.setText(getString(R.string.updating));
        imgSaveChanges.setVisibility(View.GONE);
    }

    public void onEventMainThread(FetchedProfileEvent event) {
        setProfile(event.profile);
        enableSubmitButton();
    }

    public void onEventMainThread(UpdatedProfileEvent event) {
        profileService.populateFromAPI();
        Toast.makeText(this, "Profile updated successfully..", Toast.LENGTH_SHORT).show();
        enableSubmitButton();
    }

    public void onEventMainThread(UpdatedProfileFailedEvent event) {
        Toast.makeText(this, "An error occurred: " + event.message, Toast.LENGTH_SHORT).show();
        enableSubmitButton();
    }

    public void onEventMainThread(NetworkUnavailableEvent event) {
        Toast.makeText(this, getString(R.string.update_when_available),
                Toast.LENGTH_LONG).show();
        enableSubmitButton();
    }

    public void onClickUserProfile(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            EventBus.getDefault().postSticky(new LaunchCameraEvent(profile));
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imgUser.setImageBitmap(imageBitmap);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] imageByteArray = stream.toByteArray();
            profile = eventBus.getStickyEvent(LaunchCameraEvent.class).profile;
            eventBus.removeStickyEvent(LaunchCameraEvent.class);
            profile.getUser().setPhotoImage(imageByteArray);
        }
    }

    @Override
    public void onRefresh() {
        profileService.populateFromAPI();
    }

    public static class LaunchCameraEvent {
        public Profile profile;

        public LaunchCameraEvent(Profile profile) {
            this.profile = profile;
        }
    }
}