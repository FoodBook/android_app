package tk.lenkyun.foodbook.foodbook.test;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.LargeTest;
import android.test.suitebuilder.annotation.SmallTest;

import tk.lenkyun.foodbook.foodbook.Client.DebugInfo;
import tk.lenkyun.foodbook.foodbook.Client.Service.LoginService;
import tk.lenkyun.foodbook.foodbook.Domain.Data.Authentication.UserAuthenticationInfo;
import tk.lenkyun.foodbook.foodbook.MainActivity;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ActivityInstrumentationTestCase2<MainActivity> {
    public ApplicationTest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @SmallTest
    public void testPrecondition() {
        assertNotNull("Activity is null", getActivity());
    }

    @LargeTest
    public void testLoginWithDebugUser() {
        // Crate Debug User
        UserAuthenticationInfo uInfo = new UserAuthenticationInfo(DebugInfo.USERNAME, DebugInfo.PASSWORD);
        LoginService.getInstance().login(uInfo);
    }
}