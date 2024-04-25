package lib.UI.mobile_web;

import lib.UI.SearchPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWSearchPageObject extends SearchPageObject {
    //переносим необходимые константы
    static {
        SEARCH_INPUT = "css:button#searchIcon";
        SEARCH_INPUT_ELEMENT = "css:form>input[type='search']";
        SEARCH_RESULT_BY_SUBSTRING_TPL = "xpath://div[contains(@class,'wikidata-description')][contains(text(),'{SUBSTRING}')]";
        SEARCH_RESULTS_TITLE_TPL = "//li[@title='{SUBSTRING_TITLE}']";
        SEARCH_RESULTS_DESCRIPTION_TPL = "//a/div[contains(text(),'{SUBSTRING_DESCRIPTION}')]";
        //локатор кнопки возврата
        SEARCH_CANCEL_BUTTON = "css:button.cancel";
        //локатор кнопки закрытия
        SEARCH_CLOSE_BUTTON = "css:button.clear";
        SEARCH_RESULT_ELEMENT = "css:ul.page-list>li.page-summary";
        SEARCH_EMPTY_RESULT_ELEMENT = "css:p.without-results";
        RESULT_LIST = "css:div.results-list-container>ul.page-list";
        EMPTY_RESULT_LIST = "css:p.without-results";
    }
    public MWSearchPageObject(RemoteWebDriver driver){
        super(driver);
    }
}
