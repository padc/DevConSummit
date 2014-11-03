package ph.devcon.android.profile;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.common.base.Optional;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.greenrobot.event.EventBus;
import ph.devcon.android.DevConApplication;
import ph.devcon.android.R;
import ph.devcon.android.profile.db.Profile;
import ph.devcon.android.profile.event.FetchedProfileEvent;
import ph.devcon.android.profile.service.ProfileService;
import ph.devcon.android.user.db.User;
import ph.devcon.android.util.Util;

/**
 * Created by lope on 9/13/14.
 */
public class EditUserProfileActivity extends ActionBarActivity {

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

    @InjectView(R.id.edt_contact_number)
    EditText edtContactNumber;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_edit_profile);
        DevConApplication.injectMembers(this);
        ButterKnife.inject(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
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
    public void onDestroy() {
        super.onDestroy();
        eventBus.unregister(this);
    }

    public Profile buildProfile() {
        Profile profile = new Profile();
        return profile;
    }

    public void setProfile(Profile profile) {
        Optional<Profile> profileOptional = Optional.of(profile);
        if (profileOptional.isPresent()) {
            if (Optional.of(profile.getUser()).isPresent()) {
                User user = profile.getUser();
                if (!Util.isNullOrEmpty(user.getPhotoUrl()))
                    Picasso.with(this).load(user.getPhotoUrl()).into(imgUser);
                txtFullName.setText(user.getFullName());
                edtCompanyPosition.setText(user.getPosition());
                edtCompanyName.setText(user.getCompany());
                edtLocation.setText(user.getLocation());
                edtEmailAddress.setText(user.getEmail());
                // TODO
                edtContactNumber.setText("");
                edtAboutMe.setText(user.getDescription());
                edtTech1.setText(user.getMainTechnologyTitle());
                int counter = 0;
                for (String title : user.getOtherTechnologiesTitleList()) {
                    if (counter == 0)
                        edtTech2.setText(title);
                    else if (counter == 1)
                        edtTech3.setText(title);
                }
                edtDomainName.setText(user.getWebsite());
                edtTwitter.setText(user.getTwitterHandle());
                edtFacebook.setText(user.getFacebookUrl());
            }
        }
    }

    public void onEventMainThread(FetchedProfileEvent event) {
        setProfile(event.profile);
    }

}