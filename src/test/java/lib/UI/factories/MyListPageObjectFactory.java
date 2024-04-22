package lib.UI.factories;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import lib.UI.MyListPageObject;
import lib.UI.android.AndroidMyListPageObject;
import lib.UI.android.AndroidNavigationUi;
import lib.UI.ios.iOSMyListPageObject;
import lib.UI.ios.iOSNavigationUi;
import lib.UI.mobile_web.MWMyListPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MyListPageObjectFactory {
    public static MyListPageObject get(RemoteWebDriver driver){
        //выборка драйвера
        if(Platform.getInstance().isAndroid()) {
            return new AndroidMyListPageObject(driver);
        } else if (Platform.getInstance().isIOS()) {
            return new iOSMyListPageObject(driver);
        }else {
            return new MWMyListPageObject(driver);
        }
    }
}
