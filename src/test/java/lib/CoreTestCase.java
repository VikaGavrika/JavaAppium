package lib;

import io.appium.java_client.AppiumDriver;
import junit.framework.TestCase;
import lib.UI.WelcomePageObject;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.time.Duration;

public class CoreTestCase extends TestCase {


    //аппиум драйвер будет использоваться в и других классах поэтому протектед
    protected RemoteWebDriver driver;

    @Override
    protected void setUp () throws Exception
    {
        super.setUp();
        driver = Platform.getInstance().getDriver();
        // вывести в консоль
        System.out.println("SetUp  is successful");
        //переворачиваем телефон в вертик ориентацию в начале каждого теста
        this.resetScreenOrientation();
        this.openWikiPageForMobileWeb();



    }
    @Override
    protected void tearDown() throws Exception
    {
        driver.quit();
        super.tearDown();
    }
    //методы для поворота телефона в вертикальное положение
    // (только для андройд и айос, данный метод для браузера не работает
    protected void rotateScreenPortrait()
    {    //проверяем, что драйвер является случаем Аппиум драйвера (то есть тест работает на Андройд или айос),
        //то выполняем драйвер Ротейд
        if (driver instanceof AppiumDriver){
            AppiumDriver driver = (AppiumDriver) this.driver;
            driver.rotate(ScreenOrientation.PORTRAIT);
        }else {
            //если это не так, то есть запустили тест в браузере,то ничего не делаем,
            // кроме напоминания, что данный метод для браузера не работает
            System.out.println("Method rotateScreenPortrait() does nothing for platform " +Platform.getInstance().getPlatformVar());
        }

    }
    //методы для поворота телефона в горизонт положение
    // (только для андройд и айос, данный метод для браузера не работает
    protected void rotateScreenLandscape()
    {   //проверяем, что драйвер является случаем Аппиум драйвера (то есть тест работает на Андройд или айос),
        //то выполняем драйвер Ротейд
        if (driver instanceof AppiumDriver){
            AppiumDriver driver = (AppiumDriver) this.driver;
            driver.rotate(ScreenOrientation.LANDSCAPE);
        }else {
            //если это не так, то есть запустили тест в браузере,то ничего не делаем,
            // кроме напоминания, что данный метод для браузера не работает
            System.out.println("Method rotateScreenPortrait() does nothing for platform " +Platform.getInstance().getPlatformVar());
        }

    }
    //методы для сворачивания и разворач приложения
    // (только для андройд и айос, данный метод для браузера не работает

    protected void backgroundApp(int seconds)
    {//проверяем, что драйвер является случаем Аппиум драйвера (то есть тест работает на Андройд или айос),
        //то выполняем драйвер
        if (driver instanceof AppiumDriver){
            AppiumDriver driver = (AppiumDriver) this.driver;
            driver.runAppInBackground(Duration.ofSeconds(seconds));
        }else {
            //если это не так, то есть запустили тест в браузере,то ничего не делаем,
            // кроме напоминания, что данный метод для браузера не работает
            System.out.println("Method rotateScreenPortrait() does nothing for platform " +Platform.getInstance().getPlatformVar());
        }

    }
    //метод, который делает экран всегда в портретной ориентации после завершения теста.
    //подходит только для андройд и айос, для браузера не подходит
    public void resetScreenOrientation()
    {
        if (driver instanceof AppiumDriver)
        {
            AppiumDriver driver = (AppiumDriver) this.driver;
            try {
                driver.rotate(ScreenOrientation.PORTRAIT);
            } catch (Exception e) {
            // Исключение, если телефон уже в портретной ориентации
            }
        }else {
            //если это не так, то есть запустили тест в браузере,то ничего не делаем,
            // кроме напоминания, что данный метод для браузера не работает
            System.out.println("Method rotateScreenPortrait() does nothing for platform " +Platform.getInstance().getPlatformVar());
        }
    }

    //Сбросить онбординг
    protected void skipOnboarding() {
        if(Platform.getInstance().isIOS()){
            AppiumDriver driver = (AppiumDriver) this.driver;
            WelcomePageObject WelcomePageObject = new WelcomePageObject(driver);
        WelcomePageObject.clickSkip();
        }else if(Platform.getInstance().isAndroid()){
            driver.findElementByXPath("//*[@text='Skip']").click();
        }else {
            //если это не так, то есть запустили тест в браузере,то ничего не делаем,
            // кроме напоминания, что данный метод для браузера не работает
            System.out.println("Method skipOnboarding() does nothing for platform " + Platform.getInstance().getPlatformVar());
        }

    }

    //метод открытия страницы браузера для WIKI, для айос и андр не подходит
    protected void openWikiPageForMobileWeb()
    {
        //проверяем что платформа браузер
        if(Platform.getInstance().isMW()){
            driver.get("https://en.m.wikipedia.org/");
        }else {
            //если это не так, то есть НЕ запустили тест в браузере,то ничего не делаем,
            // кроме напоминания, что данный метод для браузера не работает
            System.out.println("Method rotateScreenPortrait() does nothing for platform " +Platform.getInstance().getPlatformVar());
        }
    }
}
