package lib.UI.android;

import io.appium.java_client.AppiumDriver;
import lib.UI.MyListPageObject;

public class AndroidMyListPageObject extends MyListPageObject {
    static {
        FOLDER_BY_NAME_TPL = "xpath://*[@text='{FOLDER_NAME}']";
        ARTICLE_BY_TITLE_TPL = "xpath://*[@text='{TITLE}']";
        ARTICLE_BY_DESCRIPTION_TPL = "xpath://*[@text=\"{DESCRIPTION}\"]";

    }
    public AndroidMyListPageObject(AppiumDriver driver)
    {
        super(driver);
    }
}
