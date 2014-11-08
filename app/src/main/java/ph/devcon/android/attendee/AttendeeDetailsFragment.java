package ph.devcon.android.attendee;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.common.base.Optional;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
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

    @Inject
    AttendeeService attendeeService;

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
            }
        }
    }

    protected View buildFooterView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.footer_standard, null);
    }
}