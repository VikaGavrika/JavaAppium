package lib.UI.factories;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import lib.UI.SearchPageObject;
import lib.UI.android.AndroidSearchPageObject;
import lib.UI.ios.iOSSearchPageObject;
import lib.UI.mobile_web.MWSearchPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

//исп-ем везде, где будем выбирать платформу
public class SearchPageObjectFactory {
    public static SearchPageObject get(RemoteWebDriver driver){
        //выборка драйвера
        if(Platform.getInstance().isAndroid()) {
            return new AndroidSearchPageObject(driver);
        } else if (Platform.getInstance().isIOS()){
            return new iOSSearchPageObject(driver);
        } else {
            return new MWSearchPageObject(driver);
        }


    }
}
