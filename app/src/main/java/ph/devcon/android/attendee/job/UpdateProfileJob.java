package ph.devcon.android.attendee.job;

import com.google.common.base.Optional;
import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.Params;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import javax.inject.Inject;

import ph.devcon.android.DevConApplication;
import ph.devcon.android.auth.AuthService;
import ph.devcon.android.profile.api.EditProfileBaseResponse;
import ph.devcon.android.profile.controller.ProfileController;
import ph.devcon.android.profile.db.Profile;
import ph.devcon.android.profile.service.ProfileService;
import ph.devcon.android.program.job.Priority;
import ph.devcon.android.user.db.User;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;
import retrofit.mime.TypedString;

/**
 * Created by lope on 11/5/14.
 */
public class UpdateProfileJob extends Job {
    private static final AtomicInteger jobCounter = new AtomicInteger(0);

    private final int id;

    private final int userId;

    @Inject
    transient RestAdapter restAdapter;

    @Inject
    transient AuthService authService;

    @Inject
    transient ProfileService profileService;

    public UpdateProfileJob(int userId) {
        super(new Params(Priority.HIGH).requireNetwork().persist());
        id = jobCounter.incrementAndGet();
        this.userId = userId;
    }

    @Override
    public void onAdded() {
    }

    @Override
    public void onRun() throws Throwable {
        if (id != jobCounter.get()) {
            //looks like other fetch jobs has been added after me. no reason to keep fetching
            //many times, cancel me, let the other one fetch tweets.
            return;
        }
        Profile profile = profileService.getUserProfile(userId);
        Optional<User> userOptional = Optional.of(profile.getUser());
        User user = userOptional.get();
        ProfileController profileController = restAdapter.create(ProfileController.class);
        TypedString token = new TypedString(authService.getCachedToken());
        TypedString twitterHandle = new TypedString(user.getTwitterHandle());
        TypedString email = new TypedString(user.getEmail());
        TypedString primaryTechnology = new TypedString(user.getMainTechnologyTitle());
        TypedString position = new TypedString(user.getPosition());
        TypedString company = new TypedString(user.getCompany());
        TypedString location = new TypedString(user.getLocation());
        TypedString description = new TypedString(user.getDescription());
        TypedString website = new TypedString(user.getWebsite());
        TypedString facebookUrl = new TypedString(user.getFacebookUrl());
        TypedFile photoFile = new TypedFile("image/jpg", createTempFile(user.getPhotoImage()));
        profileController.editProfile(token, photoFile, email, primaryTechnology, position, company,
                location, description, website, facebookUrl, twitterHandle, new Callback<EditProfileBaseResponse>() {

                    @Override
                    public void success(EditProfileBaseResponse editProfileBaseResponse, Response response) {
                    }

                    @Override
                    public void failure(RetrofitError retrofitError) {
                    }
                });
    }

    public File createTempFile(byte[] data) throws IOException {
        File outputDir = DevConApplication.getInstance().getCacheDir(); // context being the Activity pointer
        File outputFile = File.createTempFile("tmp" + new Date(), ".jpg", outputDir);
        Optional<byte[]> dataOptional = Optional.fromNullable(data);
        if (dataOptional.isPresent()) {
            FileOutputStream out = new FileOutputStream(outputFile.getPath());
            out.write(data);
            out.close();
        }
        return outputFile;
    }

    @Override
    protected void onCancel() {
        //TODO show error notification
    }

    @Override
    protected boolean shouldReRunOnThrowable(Throwable throwable) {
        if (throwable instanceof SQLException) {
            // if database error then stop
            return false;
        }
        return true;
    }
}