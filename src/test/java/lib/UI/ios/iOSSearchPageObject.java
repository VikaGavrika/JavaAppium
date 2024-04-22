package lib.UI.ios;

import io.appium.java_client.AppiumDriver;
import lib.UI.SearchPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class iOSSearchPageObject extends SearchPageObject {

    //переносим необходимые константы
    static {
        SEARCH_INPUT_ELEMENT = "xpath://XCUIElementTypeSearchField[@name='Search Wikipedia']";
        SEARCH_INPUT = "xpath://XCUIElementTypeSearchField[@name='Search Wikipedia']";
        SEARCH_RESULT_BY_SUBSTRING_TPL = "xpath://XCUIElementTypeStaticText[contains(@name,'{SUBSTRING}')]";
        SEARCH_RESULTS_TITLE_TPL = "//XCUIElementTypeStaticText[contains(@name,'{SUBSTRING_TITLE}')]";
        SEARCH_RESULTS_DESCRIPTION_TPL = "//XCUIElementTypeStaticText[contains(@name,'{SUBSTRING_DESCRIPTION}')]";
        //локатор кнопки возврата
        SEARCH_CANCEL_BUTTON = "xpath://XCUIElementTypeStaticText[@name='Cancel']";
        //локатор кнопки закрытия
        SEARCH_CLOSE_BUTTON = "id:Clear text";
        SEARCH_RESULT_ELEMENT = "xpath:(//XCUIElementTypeCollectionView)[1]/XCUIElementTypeCell";
        SEARCH_EMPTY_RESULT_ELEMENT = "xpath://XCUIElementTypeStaticText[@name='No results found']";
        RESULT_LIST = "xpath:(//XCUIElementTypeCollectionView)[1]";
        EMPTY_RESULT_LIST = "id:No recent searches yet";
    }
    public iOSSearchPageObject(RemoteWebDriver driver){
        super(driver);
    }
}
