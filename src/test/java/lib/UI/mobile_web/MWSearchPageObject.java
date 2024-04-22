package lib.UI.mobile_web;

import lib.UI.SearchPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWSearchPageObject extends SearchPageObject {
    //переносим необходимые константы
    static {
        SEARCH_INPUT = "css:button#searchIcon";
        SEARCH_INPUT_ELEMENT = "css:form>input[type='search']";
        SEARCH_RESULT_BY_SUBSTRING_TPL = "xpath://div[contains(@class,'wikidata-description')][contains(text(),'{SUBSTRING}')]";
        SEARCH_RESULTS_TITLE_TPL = "//XCUIElementTypeStaticText[contains(@name,'{SUBSTRING_TITLE}')]";
        SEARCH_RESULTS_DESCRIPTION_TPL = "//XCUIElementTypeStaticText[contains(@name,'{SUBSTRING_DESCRIPTION}')]";
        //локатор кнопки возврата
        SEARCH_CANCEL_BUTTON = "css:button.cancel";
        //локатор кнопки закрытия
        SEARCH_CLOSE_BUTTON = "id:Clear text";
        SEARCH_RESULT_ELEMENT = "css:ul.mw-mf-page-list>li.page_summary";
        SEARCH_EMPTY_RESULT_ELEMENT = "xpath://XCUIElementTypeStaticText[@name='No results found']";
        RESULT_LIST = "xpath:(//XCUIElementTypeCollectionView)[1]";
        EMPTY_RESULT_LIST = "css:p.without-results";
    }
    public MWSearchPageObject(RemoteWebDriver driver){
        super(driver);
    }
}
