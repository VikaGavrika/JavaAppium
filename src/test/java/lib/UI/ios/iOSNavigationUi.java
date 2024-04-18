package lib.UI.ios;

import io.appium.java_client.AppiumDriver;
import lib.UI.NavigationUI;

public class iOSNavigationUi extends NavigationUI {
    static{
        MY_LIST_LINK =  "id:Saved";
    }
    public iOSNavigationUi(AppiumDriver driver){
        super(driver);
    }
}
