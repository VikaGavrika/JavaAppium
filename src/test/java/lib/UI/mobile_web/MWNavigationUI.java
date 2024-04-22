package lib.UI.mobile_web;

import lib.UI.NavigationUI;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWNavigationUI extends NavigationUI {
    static{
        MY_LIST_LINK =  "xpath://a[contains(@href, '/wiki/Special:Watchlist')]";
        OPEN_NAVIGATION = "xpath://label[@id='mw-mf-main-menu-button']/span";

    }
    public MWNavigationUI(RemoteWebDriver driver){
        super(driver);
    }
}
