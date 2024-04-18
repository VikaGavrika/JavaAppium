package lib.UI.factories;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import lib.UI.MyListPageObject;
import lib.UI.android.AndroidMyListPageObject;
import lib.UI.android.AndroidNavigationUi;
import lib.UI.ios.iOSMyListPageObject;
import lib.UI.ios.iOSNavigationUi;

public class MyListPageObjectFactory {
    public static MyListPageObject get(AppiumDriver driver){
        //выборка драйвера
        if(Platform.getInstance().isAndroid()) {
            return new AndroidMyListPageObject(driver);
        } else {
            return new iOSMyListPageObject(driver);
        }
    }
}
