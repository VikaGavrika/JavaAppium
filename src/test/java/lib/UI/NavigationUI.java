package lib.UI;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

abstract public class NavigationUI extends MainPageObject{
    protected static String
            MY_LIST_LINK;

    public NavigationUI(AppiumDriver driver){
        super(driver);
    }
    //навигация по приложению
    public void clickMyLists(){
        //кнопка Saved в меню
        this.waitForElementAndClick(
                MY_LIST_LINK,
                "Cannot find navigation Saved button to My list",
                20
        );
    }


}
