package lib.UI;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class NavigationUI extends MainPageObject{
    protected static String
            MY_LIST_LINK,
            OPEN_NAVIGATION;

    //инициализ драйвер
    public NavigationUI(RemoteWebDriver driver){
        super(driver);
    }

    public void openNavigation()
    {
        if(Platform.getInstance().isMW()){
            this.waitForElementAndClick(OPEN_NAVIGATION,"Cannot find and click open navigation button",15);

        }else {
            System.out.println("Method openNavigation() does nothing for platform " + Platform.getInstance().getPlatformVar());
        }
    }


    //навигация по приложению
    public void clickMyLists()throws InterruptedException {
        Thread.sleep(10000);

        if (Platform.getInstance().isMW()){
            this.tryClickElementWithFewAttempts(
                    MY_LIST_LINK,
                    "Cannot find navigation Saved button to My list",
                    20
            );
        }

    }



}
