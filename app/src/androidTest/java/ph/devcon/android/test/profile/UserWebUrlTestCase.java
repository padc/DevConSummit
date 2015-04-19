package ph.devcon.android.test.profile;

import android.test.suitebuilder.annotation.SmallTest;

import ph.devcon.android.test.BaseApplicationTestCase;
import ph.devcon.android.user.db.User;

/**
 * Created by lope on 11/11/14.
 */
public class UserWebUrlTestCase extends BaseApplicationTestCase {

    @SmallTest
    public void testShouldReturnDomainOnly() {
        User user = new User();
        user.setWebsite("http://www.devcon.ph");
        String webSiteDomain = user.getWebsiteDomain();
        assertEquals("devcon.ph", webSiteDomain);
        user.setWebsite("http://devcon.ph");
        webSiteDomain = user.getWebsiteDomain();
        assertEquals("devcon.ph", webSiteDomain);
        user.setWebsite("devcon.ph");
        webSiteDomain = user.getWebsiteDomain();
        assertEquals("devcon.ph", webSiteDomain);
    }

    @SmallTest
    public void testShouldReturnFullUrlAlyways() {
        User user = new User();
        user.setWebsite("http://www.devcon.ph");
        String website = user.getWebsite();
        assertEquals("http://www.devcon.ph", website);
        user.setWebsite("http://devcon.ph");
        website = user.getWebsite();
        assertEquals("http://devcon.ph", website);
        user.setWebsite("devcon.ph");
        website = user.getWebsite();
        assertEquals("http://devcon.ph", website);
    }

}