package lib.UI.mobile_web;

import lib.UI.ArticlePageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWArticlePageObject extends ArticlePageObject {
    static {
        TITLE_TPL = "css:#content h1";
        TITLE_TPL2 = "css:#content h1";
        FOOTER_ELEMENT = "css:footer";
        OPTIONS_ADD_TO_MY_LIST_BUTTON = "css:span.minerva-icon.minerva-icon--star-base20";
        OPTIONS_REMOVE_FROM_MY_LIST_BUTTON = "css:span.minerva-icon.minerva-icon--unStar-progressive";
        CLOSE_ARTICLE_BUTTON = "id:Back";
        CANCEL_BUTTON = "id:Cancel";

    }
    public MWArticlePageObject(RemoteWebDriver driver)
    {
        super(driver);
    }
}
