package lib.UI.ios;

import io.appium.java_client.AppiumDriver;
import lib.UI.NavigationUI;
import org.openqa.selenium.remote.RemoteWebDriver;

public class iOSNavigationUi extends NavigationUI {
    static{
        MY_LIST_LINK =  "id:Saved";
    }
    public iOSNavigationUi(RemoteWebDriver driver){
        super(driver);
    }
}
