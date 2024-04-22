package lib.UI.android;

import io.appium.java_client.AppiumDriver;
import lib.UI.NavigationUI;
import org.openqa.selenium.remote.RemoteWebDriver;

public class AndroidNavigationUi  extends NavigationUI {
    static{
        MY_LIST_LINK =  "xpath://android.widget.FrameLayout[@content-desc=\"Saved\"]";
    }
    public AndroidNavigationUi(RemoteWebDriver driver){
        super(driver);
    }
}
