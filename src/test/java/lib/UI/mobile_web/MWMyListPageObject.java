package lib.UI.mobile_web;

import lib.UI.MyListPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWMyListPageObject extends MyListPageObject {
    static {
        CLOSE_MODAL_WINDOW_BUTTON = "id:Close";
        ARTICLE_BY_TITLE_TPL = "xpath://h3[contains(text(),'{TITLE}')]";
        REMOVE_FROM_SAVED_BUTTON = "xpath://h3[contains(text(),'{TITLE}')]/../../a[contains(@class,'watched')]";
        DELETE_BUTTON = "id:swipe action delete";
        ARTICLE_CONTAINER = "xpath://div[@id='mw-content-text']/ul/li]";
        ARTICLE_BY_DESCRIPTION_TPL = "xpath://XCUIElementTypeStaticText[@name='{DESCRIPTION}']";
        DESCRIPTION = "xpath://XCUIElementTypeStaticText";

    }
    public MWMyListPageObject(RemoteWebDriver driver) {
        super(driver);
    }
}
