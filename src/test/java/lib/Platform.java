package lib;



import com.sun.javafx.collections.MappingChange;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.util.Map;
import java.util.HashMap;


public class Platform {
    //значения, с которыми будем сравнивать переменные среды
    private static final String PLATFORM_IOS = "ios";
    private static final String PLATFORM_ANDROID = "android";
    private static final String PLATFORM_MOBILE_WEB = "mobile_web";

    private static final String APPIUM_URL = "http://127.0.0.1:4723";

    //чтобы каждый раз не собирать платформу, поэтому исп-ем паттерн проектирование сингл тон,
    //суть в том, что объект класса создается только один раз во время выполнения кода и хранится в одном из полей класса
    private static Platform instance;
    //приватный конструктор
    private  Platform() {}
    //вызов платформы
    public static Platform getInstance(){
        if (instance == null){
            instance = new Platform();
        }
        return instance;
    }

    //метод отвечающий за выбор драйвера
    public RemoteWebDriver getDriver() throws Exception{
        URL URL = new URL(APPIUM_URL);
        if (this.isAndroid()){
            return new AndroidDriver(URL, this.getAndroidCapabilities());
        } else if (this.isIOS()){
            return new IOSDriver(URL, this.getIOSCapabilities());
        } else if (this.isMW()){
            return new ChromeDriver(this.getMWChromeOptions());

        } else {
            throw new Exception ("Cannot detected type of the Driver. Platform value: " +this.getPlatformVar());
        }
    }



    //методы,которые определяют явл-ся ли платформа андройд или айос или браузер
    public boolean isAndroid(){
        return isPlatform(PLATFORM_ANDROID);
    }
    public boolean isIOS(){
        return isPlatform(PLATFORM_IOS);
    }
    public boolean isMW(){
        return isPlatform(PLATFORM_MOBILE_WEB);
    }


    //метод, который запускает нужную платформу
    private DesiredCapabilities getAndroidCapabilities() {

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("appium:platformName","Android");
        capabilities.setCapability("appium:deviceName","AndroidTestDevice");
        capabilities.setCapability("appium:platformVersion","14.0");
        capabilities.setCapability("appium:automationName","UiAutomator2");
        capabilities.setCapability("appium:appPackage","org.wikipedia");
        capabilities.setCapability("appium:appActivity",".main.MainActivity");
        capabilities.setCapability("appium:app","/Users/citylink/Documents/JavaAppiumAutomation/apks/wikipedia2023-12-12.apk");
        //метод возвращает копабилитис
        return capabilities;

        }
    private DesiredCapabilities getIOSCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("appium:platformName", "iOS");
        capabilities.setCapability("appium:deviceName", "iPhone11");
        capabilities.setCapability("appium:platformVersion", "17.4");
        capabilities.setCapability("appium:automationName", "XCUITest");
        capabilities.setCapability("appium:appPackage", "wikipedia.app");
        capabilities.setCapability("appium:appActivity", ".main.MainActivity");
        capabilities.setCapability("appium:app", "/Users/citylink/Documents/JavaAppiumAutomation/apks/Wikipedia.app");

        //метод возвращает копабилитис
        return capabilities;
    }

    //метод, который запускает браузер Хром
    private ChromeOptions getMWChromeOptions(){
        //записываем опции браузера, которые будем использовать
        Map<String, Integer> deviceMetrics = new HashMap<>();
        //задаем параметры девайса Pixel7
        deviceMetrics.put("width", 412);
        deviceMetrics.put("height", 915);
        deviceMetrics.put("pixelRatio", 260);
        //параметр юзерагента
        Map<String, Object> mobileEmulation = new HashMap<>();
        //передаем существующие метрики девайса
        mobileEmulation.put("deviceMetrics", deviceMetrics);
        //передаем метрики по юзерагенту. юзерагент представляет собой девайс для браузера, как моб устройство на Андройд
        mobileEmulation.put("userAgent", "Mozilla/5.0 (Linux; Android 13; en-us; Pixel 7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.6312.122 Safari/537.36 ");

        //задаем параметры окна хром
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("window-size=530,1015");

        //возвращаем
        return chromeOptions;

    }


    //метод, который будет сравнивать нашу платформу с той переменной, которая приходит к нему на вход
    private boolean isPlatform(String my_platform){
        //сравниваем 2 платформы
        //получаем премен окружения
        String platform = this.getPlatformVar();
        //сравниваем переменную, которая приходит на вход с перемен окруж
        return my_platform.equals(platform);

    }

    //метод, который отвечает за получение переменной окружения
    public String getPlatformVar()
    {
        return System.getenv("PLATFORM");
    }

}
