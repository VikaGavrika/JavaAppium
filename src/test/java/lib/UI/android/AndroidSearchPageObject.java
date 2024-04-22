package lib.UI.android;

import io.appium.java_client.AppiumDriver;
import lib.UI.SearchPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class AndroidSearchPageObject extends SearchPageObject {
    //переносим необходимые константы
    static {
            SEARCH_INPUT_ELEMENT= "xpath://*[@text='Search Wikipedia']";
            SEARCH_INPUT = "xpath://*[@text='Search Wikipedia']";
            SEARCH_RESULT_BY_SUBSTRING_TPL = "xpath://*[@resource-id='org.wikipedia:id/search_results_list']//*[@text='{SUBSTRING}']";
            SEARCH_RESULTS_TITLE_TPL = "xpath://*[@resource-id=\"org.wikipedia:id/page_list_item_title\" and contains(@text, \"{SUBSTRING_TITLE}\")]";
            SEARCH_RESULTS_DESCRIPTION_TPL = "xpath://*[@resource-id=\"org.wikipedia:id/page_list_item_description\" and contains(@text, \"{SUBSTRING_DESCRIPTION}\")]";
            //локатор кнопки возврата
            SEARCH_CANCEL_BUTTON = "xpath://*[@content-desc='Navigate up']";
            //локатор кнопки закрытия
            SEARCH_CLOSE_BUTTON = "id:org.wikipedia:id/search_close_btn";
            SEARCH_RESULT_ELEMENT = "xpath://*[@resource-id=\"org.wikipedia:id/search_results_list\"]/android.view.ViewGroup";
            SEARCH_EMPTY_RESULT_ELEMENT = "xpath://*[@text = 'No results']";
            RESULT_LIST = "id:org.wikipedia:id/search_results_list";
            EMPTY_RESULT_LIST = "id:org.wikipedia:id/search_empty_container";
    }


    //конструктор класса
    public AndroidSearchPageObject(RemoteWebDriver driver){
        super(driver);
    }



}
