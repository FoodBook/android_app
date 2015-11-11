package tk.lenkyun.foodbook.foodbook.test.Service;

import junit.framework.TestCase;

import tk.lenkyun.foodbook.foodbook.Client.DebugInfo;
import tk.lenkyun.foodbook.foodbook.Client.Service.LoginService;
import tk.lenkyun.foodbook.foodbook.Domain.Data.Authentication.UserAuthenticationInfo;

/**
 * Created by lenkyun on 20/10/2558.
 */
public class LoginServiceTest extends TestCase {
    protected void setUp() {
        // Server must can login with debug user when testing
        // However test user don't have any real result
        UserAuthenticationInfo uInfo = new UserAuthenticationInfo(DebugInfo.USERNAME, DebugInfo.PASSWORD);
        LoginService.getInstance().login(uInfo);
    }

    protected void tearDown() {
        LoginService.getInstance().logout();
    }
}
