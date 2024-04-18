package lib.UI.ios;

import io.appium.java_client.AppiumDriver;
import lib.UI.MyListPageObject;

public class iOSMyListPageObject extends MyListPageObject {
    static {
        CLOSE_MODAL_WINDOW_BUTTON = "id:Close";
        ARTICLE_BY_TITLE_TPL = "xpath://XCUIElementTypeStaticText[contains(@name,'{TITLE}')]";
        DELETE_BUTTON = "id:swipe action delete";
        ARTICLE_CONTAINER = "xpath://XCUIElementTypeCollectionView/XCUIElementTypeCell";
        ARTICLE_BY_DESCRIPTION_TPL = "xpath://XCUIElementTypeStaticText[@name='{DESCRIPTION}']";
        DESCRIPTION = "xpath://XCUIElementTypeStaticText";


    }
    public iOSMyListPageObject(AppiumDriver driver)
    {
        super(driver);
    }
}
