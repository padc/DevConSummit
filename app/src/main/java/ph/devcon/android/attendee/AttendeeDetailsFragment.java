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

package ph.devcon.android.attendee;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.base.Optional;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import ph.devcon.android.DevConApplication;
import ph.devcon.android.R;
import ph.devcon.android.attendee.db.Attendee;
import ph.devcon.android.attendee.service.AttendeeService;
import ph.devcon.android.user.db.User;
import ph.devcon.android.util.Util;

/**
 * Created by lope on 10/9/14.
 */
public class AttendeeDetailsFragment extends Fragment {
    public static final String ID = "attendee_id";
    @InjectView(R.id.img_background_top)
    ImageView imgBackgroundTop;

    @InjectView(R.id.img_attendee)
    ImageView imgAttendee;

    @InjectView(R.id.txt_full_name)
    TextView txtFullName;

    @InjectView(R.id.txt_position)
    TextView txtPosition;

    @InjectView(R.id.txt_twitter)
    TextView txtTwitter;

    @InjectView(R.id.txt_website)
    TextView txtWebsite;

    @InjectView(R.id.txt_email_and_contact)
    TextView txtEmailAndContact;

    @InjectView(R.id.txt_about_title)
    TextView txtAboutTitle;

    @InjectView(R.id.txt_about_content)
    TextView txtAboutContent;

    @InjectView(R.id.txt_technology_stack)
    TextView txtTechnologyStack;

    @InjectView(R.id.btn_profile_website)
    ImageView btnProfileWebsite;

    @InjectView(R.id.btn_profile_twitter)
    ImageView btnProfileTwitter;

    @InjectView(R.id.btn_profile_facebook)
    ImageView btnProfileFacebook;

    @Inject
    AttendeeService attendeeService;
    ShareActionProvider mShareActionProvider;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater
                .inflate(R.layout.fragment_attendee_details, container, false);
        DevConApplication.injectMembers(this);
        ButterKnife.inject(this, rootView);
        Attendee attendee = attendeeService.getAttendee(getArguments().getInt(ID));
        init(attendee);
        return rootView;
    }

    /**
     * http://android-developers.blogspot.com/2012/02/share-with-intents.html
     *
     * @param menu
     * @param inflater
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        // Inflate menu resource file.
        getActivity().getMenuInflater().inflate(R.menu.news, menu);

        // Locate MenuItem with ShareActionProvider
        MenuItem item = menu.findItem(R.id.menu_item_share);

        // Fetch and store ShareActionProvider
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        mShareActionProvider.setShareHistoryFileName("custom_share_history.xml");
    }

    public void init(Attendee attendee) {
        Optional<Attendee> attendeeOptional = Optional.of(attendee);
        if (attendeeOptional.isPresent()) {
            Optional<User> userOptional = Optional.of(attendeeOptional.get().getUser());
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                txtFullName.setText(user.getFullName());
                txtPosition.setText(user.getPositionAndCompany());
                txtTwitter.setText(user.getTwitterHandle());
                txtWebsite.setText(user.getWebsite());
                txtEmailAndContact.setText(user.getEmailAndContact());
                txtAboutTitle.setText(user.getAboutTitle());
                txtAboutContent.setText(user.getDescription());
                if (!Util.isNullOrEmpty(user.getPhotoUrl())) {
                    Picasso.with(getActivity()).load(user.getPhotoUrl()).into(imgAttendee);
                    Picasso.with(getActivity()).load(user.getPhotoUrl()).transform(new Util.BlurTransformation(getActivity().getApplicationContext())).into(imgBackgroundTop);
                }
                txtTechnologyStack.setText(user.getPrettyTechnologyList());
                btnProfileWebsite.setTag(user.getWebsite());
                btnProfileTwitter.setTag(user.getTwitterHandle());
                btnProfileFacebook.setTag(user.getFacebookHandle());
            }
        }
    }

    @OnClick(R.id.btn_profile_website)
    public void onClickProfileWebsite(View view) {
        String webUrl = (String) view.getTag();
        if (!Util.isNullOrEmpty(webUrl)) {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(webUrl));
            startActivity(intent);
        } else
            Toast.makeText(getActivity(), "User website is empty..", Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.btn_profile_twitter)
    public void onClickProfileTwitter(View view) {
        String twitterHandle = (String) view.getTag();
        if (!Util.isNullOrEmpty(twitterHandle))
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=" + twitterHandle)));
            } catch (Exception e) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/#!/" + twitterHandle)));
            }
        else
            Toast.makeText(getActivity(), "Twitter handle is empty..", Toast.LENGTH_LONG).show();
    }

    /**
     * use this solution:
     * http://stackoverflow.com/questions/4810803/open-facebook-page-from-android-app
     * <p/>
     * 1) Go to https://graph.facebook.com/<user_name_here> (https://graph.facebook.com/fsintents for instance)
     * 2) Copy your id
     * <p/>
     * Use this method
     *
     * @param view
     */
    @OnClick(R.id.btn_profile_facebook)
    public void onClickProfileFacebook(View view) {
        String facebookHandle = (String) view.getTag();
        if (!Util.isNullOrEmpty(facebookHandle)) {
            Intent intent;
            try {
                getActivity().getPackageManager()
                        .getPackageInfo("com.facebook.katana", 0); //Checks if FB is even installed.
                intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(facebookHandle)); //Trys to make intent with FB's URI
            } catch (Exception e) {
                intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(facebookHandle)); //catches and opens a url to the desired page
            }
            startActivity(intent);
        } else
            Toast.makeText(getActivity(), "Facebook handle is empty..", Toast.LENGTH_LONG).show();
    }

    protected View buildFooterView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.footer_standard, null);
    }
}