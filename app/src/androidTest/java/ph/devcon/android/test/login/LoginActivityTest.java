package ph.devcon.android.test.login;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.MediumTest;

import ph.devcon.android.R;
import ph.devcon.android.login.LoginActivity;
import ph.devcon.android.test.auth.Mocker;

import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.click;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.scrollTo;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.typeText;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;

/**
 * Created by lope on 11/16/14.
 */

@MediumTest
public class LoginActivityTest extends ActivityInstrumentationTestCase2<LoginActivity> {

    public LoginActivityTest() {
        super(LoginActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        getActivity();
    }

    @MediumTest
    public void testLoginValidUser() {
        onView(withId(R.id.edt_email_address))
                .perform(typeText(Mocker.USERNAME_VALID));
        onView(withId(R.id.edt_password))
                .perform(typeText(Mocker.PASSWORD_VALID));
        onView(withId(R.id.btn_login)).perform(scrollTo(), click());
//        onView(withId(R.id.cont_main_list)).check(ViewAssertions.matches(isDisplayed()));
    }

}
