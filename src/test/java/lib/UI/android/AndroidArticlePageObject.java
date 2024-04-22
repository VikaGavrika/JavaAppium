package lib.UI.android;

import io.appium.java_client.AppiumDriver;
import lib.UI.ArticlePageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class AndroidArticlePageObject extends ArticlePageObject {
    static {
        TITLE_TPL = "xpath://*[@text=\"{SUBSTRING}\"]";

        FOOTER_ELEMENT = "xpath://*[@text=\"View article in browser\"]";
        OPTIONS_BUTTON = "xpath://android.widget.ImageView[@content-desc=\"More options\"]";
        TOOLBAR_BUTTON = "xpath://android.widget.TextView[@resource-id=\"org.wikipedia:id/customize_toolbar\"]";
        NAVIGATE_BUTTON = "xpath://*[@content-desc='Navigate up']";
        SAVE_BUTTON = "xpath://android.widget.TextView[@resource-id=\"org.wikipedia:id/page_save\"]";
        OPTIONS_ADD_TO_MY_LIST_BUTTON = "xpath://*[@text=\"Add to list\"]";
        MY_LIST_NAME_INPUT = "xpath://*[@resource-id=\"org.wikipedia:id/text_input_container\"]";
        MY_LIST_OK_BUTTON = "xpath://*[@text=\"OK\"]";
        OPTIONS_VIEW_LIST_BUTTON = "xpath://*[@text=\"View list\"]";
    }
    public AndroidArticlePageObject(RemoteWebDriver driver)
    {
        super(driver);
    }
}
