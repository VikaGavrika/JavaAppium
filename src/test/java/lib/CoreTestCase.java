package lib;

import io.appium.java_client.AppiumDriver;

import io.qameta.allure.Step;
import lib.UI.WelcomePageObject;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.FileOutputStream;
import java.time.Duration;
import java.util.Properties;

public class CoreTestCase {


    //аппиум драйвер будет использоваться в и других классах поэтому протектед
    protected RemoteWebDriver driver;

    @Before
    @Step("Запуск драйвера и сессии")
    public void setUp () throws Exception
    {
        driver = Platform.getInstance().getDriver();
        //вызываем метод запись окружения в файл окружения для аллюр
        this.createAllurePropertyFile();
        // вывести в консоль
        System.out.println("SetUp  is successful");
        //переворачиваем телефон в вертик ориентацию в начале каждого теста
        this.resetScreenOrientation();
        this.openWikiPageForMobileWeb();

    }
    @After
    @Step("Прекращение драйвера и окончание сессии")
    public void tearDown()
    {
        driver.quit();

    }
    //методы для поворота телефона в вертикальное положение
    // (только для андройд и айос, данный метод для браузера не работает
    @Step("Повернуть экран в вертикальную ориентацию")
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
    @Step("Повернуть экран в горизонтальную ориентацию")
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
    @Step("Свернуть и сразу развернуть приложение (не работает на веб версии")
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
    @Step("Поворачивать экран в горизонтальную ориентацию по-умолчанию")
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
    @Step("Пройти онбординг")
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
    @Step("Открыть страницу приложения (для веб версии)")
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
    //добавление описания окружения в отчет Аллюр
    //метод генерирования файла окружения
    private void createAllurePropertyFile(){
        //получаем путь до директории аллюр
        String path = System.getProperty("allure.results.directory");
        try{
            Properties props = new Properties();
            FileOutputStream fos = new FileOutputStream(path + "/environment.properties");
            //получаем текущее значение для переменной окружения
            props.setProperty("Environment", Platform.getInstance().getPlatformVar());
            //пишем это в файл environment.properties
            props.store(fos, "See https://allurereport.org/docs/#_environment");
            fos.close();
        } catch (Exception e){
            System.err.println("IO problem when writing allure properties file");
            e.printStackTrace();
        }

    }
}
