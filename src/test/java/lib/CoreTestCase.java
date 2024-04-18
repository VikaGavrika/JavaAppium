package lib;

import io.appium.java_client.AppiumDriver;
import junit.framework.TestCase;
import lib.UI.WelcomePageObject;
import org.openqa.selenium.ScreenOrientation;

import java.time.Duration;

public class CoreTestCase extends TestCase {


    //аппиум драйвер будет использоваться в и других классах поэтому протектед
    protected AppiumDriver driver;

    @Override
    protected void setUp () throws Exception
    {
        super.setUp();
        driver = Platform.getInstance().getDriver();
        // вывести в консоль
        System.out.println("SetUp  is successful");
        //переворачиваем телефон в вертик ориентацию в начале каждого теста
        this.resetScreenOrientation();



    }
    @Override
    protected void tearDown() throws Exception
    {
        driver.quit();
        super.tearDown();
    }
    //методы для поворота телефона
    protected void rotateScreenPortrait(){
        driver.rotate(ScreenOrientation.PORTRAIT);
    }
    protected void rotateScreenLandscape(){
        driver.rotate(ScreenOrientation.LANDSCAPE);
    }

    protected void backgroundApp(int seconds){
        driver.runAppInBackground(Duration.ofSeconds(seconds));
    }
    //метод, который делает экран всегда в портретной ориентации после завершения теста.
    public void resetScreenOrientation() {
        try {
            driver.rotate(ScreenOrientation.PORTRAIT);
        } catch (Exception e) {
            // Исключение, если телефон уже в портретной ориентации
        }
    }
    //
    protected void skipOnboarding() {
        if(Platform.getInstance().isIOS()){
            WelcomePageObject WelcomePageObject = new WelcomePageObject(driver);
        WelcomePageObject.clickSkip();
        }else if(Platform.getInstance().isAndroid()){
            driver.findElementByXPath("//*[@text='Skip']").click();
        }
    }
}
