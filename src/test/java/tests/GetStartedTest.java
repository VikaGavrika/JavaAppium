package tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import lib.CoreTestCase;
import lib.Platform;
import lib.UI.WelcomePageObject;
import org.junit.Test;

public class GetStartedTest extends CoreTestCase {

    @Test
    @Features(value = {@Feature(value="Onboarding")})
    @DisplayName("Онбординг")
    @Description("В Айос версии проходим онбординг полностью, в андойде версии сбрасывается онбординг, в веб версии прохождения онбординга нет")
    @Step("Старт прохождение онбординга")
    //критичность теста
    @Severity(value = SeverityLevel.MINOR)
    public void testPassThroughWelcome(){
        if (Platform.getInstance().isAndroid()){
            //сброс онбординга
            this.skipOnboarding();
            return;
        }

        //инициализация
        WelcomePageObject WelcomePageObject = new WelcomePageObject(driver);
        //находим ссылку на 1 онбор
        WelcomePageObject.waitForLearnMoreLink();
        //клик на кнопке некст
        WelcomePageObject.clickNextButton();
        //находим заголовок 2 онборд
        WelcomePageObject.waitForOnboardingTitle("New ways to explore");
        //клик на кнопке некст
        WelcomePageObject.clickNextButton();
        //находим заголовок 3 онборд
        WelcomePageObject.waitForOnboardingTitle("Search in over 300 languages");
        //клик на кнопке некст
        WelcomePageObject.clickNextButton();
        //находим заголовок 4 онборд
        WelcomePageObject.waitForOnboardingTitle("Data & Privacy");
        //клик на кнопке get started
        WelcomePageObject.clickGetStartedButton();

    }

}
