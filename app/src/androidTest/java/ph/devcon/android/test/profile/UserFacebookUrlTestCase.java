package ph.devcon.android.test.profile;

import android.test.suitebuilder.annotation.SmallTest;

import ph.devcon.android.test.BaseApplicationTestCase;
import ph.devcon.android.user.db.User;

/**
 * Created by lope on 11/11/14.
 */
public class UserFacebookUrlTestCase extends BaseApplicationTestCase {

    @SmallTest
    public void testShouldReturnFacebookHandleIfFacebookHandle() {
        User user = new User();
        user.setFacebookUrl("lope.emano");
        String fbHandle = user.getFacebookHandle();
        assertEquals("lope.emano", fbHandle);
    }

    @SmallTest
    public void testShouldReturnFacebookHandleIfUrlOnly() {
        User user = new User();
        user.setFacebookUrl("https://www.facebook.com/lope.emano");
        String fbHandle = user.getFacebookHandle();
        assertEquals("lope.emano", fbHandle);
        user.setFacebookUrl("https://facebook.com/lope.emano");
        fbHandle = user.getFacebookHandle();
        assertEquals("lope.emano", fbHandle);
        user.setFacebookUrl("http://www.facebook.com/lope.emano");
        fbHandle = user.getFacebookHandle();
        assertEquals("lope.emano", fbHandle);
        user.setFacebookUrl("http://facebook.com/lope.emano");
        fbHandle = user.getFacebookHandle();
        assertEquals("lope.emano", fbHandle);
    }

    @SmallTest
    public void testShouldReturnUrlIfFacebookHandleOnly() {
        User user = new User();
        user.setFacebookUrl("lope.emano");
        String facebookUrl = user.getFacebookUrl();
        assertEquals("https://www.facebook.com/lope.emano", facebookUrl);
    }

    @SmallTest
    public void testShouldReturnUrlIfFacebookUrl() {
        User user = new User();
        user.setFacebookUrl("https://www.facebook.com/lope.emano");
        String facebookUrl = user.getFacebookUrl();
        assertEquals("https://www.facebook.com/lope.emano", facebookUrl);
        user.setFacebookUrl("https://www.facebook.com/lope.emano");
        facebookUrl = user.getFacebookUrl();
        assertEquals("https://www.facebook.com/lope.emano", facebookUrl);
    }

}