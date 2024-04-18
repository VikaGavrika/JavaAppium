package lib.UI.factories;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import lib.UI.NavigationUI;
import lib.UI.android.AndroidArticlePageObject;
import lib.UI.android.AndroidNavigationUi;
import lib.UI.ios.iOSArticlePageObject;
import lib.UI.ios.iOSNavigationUi;

public class NavigationUiFactory {
    public static NavigationUI get(AppiumDriver driver){
        //выборка драйвера
        if(Platform.getInstance().isAndroid()) {
            return new AndroidNavigationUi(driver);
        } else {
            return new iOSNavigationUi(driver);
        }
    }
}
