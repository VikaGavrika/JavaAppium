package lib.UI.factories;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import lib.UI.SearchPageObject;
import lib.UI.android.AndroidSearchPageObject;
import lib.UI.ios.iOSSearchPageObject;

//исп-ем везде, где будем выбирать платформу
public class SearchPageObjectFactory {
    public static SearchPageObject get(AppiumDriver driver){
        //выборка драйвера
        if(Platform.getInstance().isAndroid()) {
            return new AndroidSearchPageObject(driver);
        } else {
            return new iOSSearchPageObject(driver);
        }
    }
}
