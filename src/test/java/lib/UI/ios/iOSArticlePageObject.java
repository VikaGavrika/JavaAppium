package lib.UI.ios;

import io.appium.java_client.AppiumDriver;
import lib.UI.ArticlePageObject;

public class iOSArticlePageObject extends ArticlePageObject {
    static {
        TITLE_TPL = "id:Java (programming language)";
        TITLE_TPL2 = "id:Appium";
        FOOTER_ELEMENT = "id:View article in browser";
        OPTIONS_ADD_TO_MY_LIST_BUTTON = "id:Save for later";
        CLOSE_ARTICLE_BUTTON = "id:Back";
        CANCEL_BUTTON = "id:Cancel";


    }
    public iOSArticlePageObject(AppiumDriver driver)
    {
        super(driver);
    }
}
