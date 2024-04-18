package lib.UI.android;

import io.appium.java_client.AppiumDriver;
import lib.UI.NavigationUI;

public class AndroidNavigationUi  extends NavigationUI {
    static{
        MY_LIST_LINK =  "xpath://android.widget.FrameLayout[@content-desc=\"Saved\"]";
    }
    public AndroidNavigationUi(AppiumDriver driver){
        super(driver);
    }
}
