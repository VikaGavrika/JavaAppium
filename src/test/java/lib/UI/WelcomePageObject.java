package lib.UI;

import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;


public class WelcomePageObject extends MainPageObject{
    private static final String
            LINK_ELEMENT = "xpath://*[@name=\"Learn more about Wikipedia\"]";
    private static final String
            NEXT_BUTTON = "xpath://*[@name=\"Next\"]";
    private static final String
            GET_STARTED_BUTTON = "xpath://*[@name=\"Get started\"]";
    private static final String
            TITLE_ONBOARDING_TPL = "xpath://*[@name=\"{TITLE}\"]";
    private static final String
            SKIP = "xpath://*[@name='Skip']";


    /*TEMPLATES METHODS */
    //метод, который подставляет подстроку по шаблону
    @Step("Подставляет подстроку '{onboarding_title}'")
    private static String getResultTitleElement(String onboarding_title){
        //меняем значение переменной TITLE на строчку onboarding_title
        return TITLE_ONBOARDING_TPL.replace("{TITLE}", onboarding_title);
    }

    /*TEMPLATES METHODS */


    //инициализация драйвера

    public WelcomePageObject(RemoteWebDriver driver){
        super(driver);
    }


    //поиск характерного элемента на странице онбординга
    //ссылку на 1 онбординге
    @Step("Поиск  ссылки на странице 1онбординга")
    public void waitForLearnMoreLink(){
        this.waitForElementPresent(LINK_ELEMENT,"Cannot find link",15);
    }
    //заголовок 2,3,4 онбордингов
    @Step("Поиск  заголовка на странице онбординга")
    public void waitForOnboardingTitle(String onboarding_title){
        String onboarding_title_xpath = getResultTitleElement(onboarding_title);
        this.waitForElementPresent(onboarding_title_xpath,"Cannot find onboarding title " +onboarding_title,15);
    }

    //клик по кнопке
    @Step("Клик по кнопке 'следующий' онбординга")
    public void clickNextButton(){
        this.waitForElementAndClick(NEXT_BUTTON,"Cannot find next button and click",15);
    }
    //клик по кнопке
    @Step("Клик по кнопке 'начать' онбординга")
    public void clickGetStartedButton(){
        this.waitForElementAndClick(GET_STARTED_BUTTON,"Cannot find get started button and click",15);
    }

    //скипнуть онбординг
    @Step("Клик по кнопке 'сбросить' онбординга")
    public void clickSkip(){
       this.waitForElementAndClick(SKIP,"Cannot find and click skip button",15);
    }


}
