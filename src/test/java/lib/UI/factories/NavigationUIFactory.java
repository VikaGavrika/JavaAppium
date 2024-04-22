package lib.UI.factories;

import lib.Platform;
import lib.UI.NavigationUI;
import lib.UI.android.AndroidNavigationUi;
import lib.UI.ios.iOSNavigationUi;
import lib.UI.mobile_web.MWNavigationUI;
import org.openqa.selenium.remote.RemoteWebDriver;

public class NavigationUIFactory {
    public static NavigationUI get(RemoteWebDriver driver) {
        //выборка драйвера
        if (Platform.getInstance().isAndroid()) {
            return new AndroidNavigationUi(driver);
        } else if (Platform.getInstance().isIOS()) {
            return new iOSNavigationUi(driver);
        } else {
            return new MWNavigationUI(driver);
        }
    }
}
