package lib.UI;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;



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
    private static String getResultTitleElement(String onboarding_title){
        //меняем значение переменной TITLE на строчку onboarding_title
        return TITLE_ONBOARDING_TPL.replace("{TITLE}", onboarding_title);
    }

    /*TEMPLATES METHODS */


    //инициализация драйвера
    public WelcomePageObject(AppiumDriver driver){
        super(driver);
    }
    //поиск характерного элемента на странице онбординга
    //ссылку на 1 онбординге
    public void waitForLearnMoreLink(){
        this.waitForElementPresent(LINK_ELEMENT,"Cannot find link",15);
    }
    //заголовок 2,3,4 онбордингов
    public void waitForOnboardingTitle(String onboarding_title){
        String onboarding_title_xpath = getResultTitleElement(onboarding_title);
        this.waitForElementPresent(onboarding_title_xpath,"Cannot find onboarding title " +onboarding_title,15);
    }

    //клик по кнопке
    public void clickNextButton(){
        this.waitForElementAndClick(NEXT_BUTTON,"Cannot find next button and click",15);
    }
    //клик по кнопке
    public void clickGetStartedButton(){
        this.waitForElementAndClick(GET_STARTED_BUTTON,"Cannot find get started button and click",15);
    }

    //скипнуть онбординг
    public void clickSkip(){
       this.waitForElementAndClick(SKIP,"Cannot find and click skip button",15);
    }


}
