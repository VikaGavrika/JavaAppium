package lib.UI;


import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

//Пэйджобжект для авторизации. Пока только для мобайл веб
public class AuthorizationPageObject extends MainPageObject
{
    private static final String
            OVERLAY = "css:div.drawer.drawer-container__drawer.position-fixed.visible",
            LOGIN_BUTTON = "css:.drawer.visible > a",
            LOGIN_INPUT = "xpath://input[@id='wpName1']",
            PASSWORD_INPUT = "xpath://input[@id='wpPassword1']",
            SUBMIT_BUTTON = "xpath://button[@id='wpLoginAttempt']";

    public AuthorizationPageObject (RemoteWebDriver driver){
        super (driver);
    }

    //методы авторизации
    //метод, который кликает по кнопке логин. Но при эом с временем ожидания пока произойдет подписание на предыдущее действие,то есть нажатие звездочки избранное
    @Step("Клик по кнопке Логин")
    public void clickAuthButton() throws InterruptedException {
        //ждем пока исчезнет оверлэй
        Thread.sleep(5000);

        this.waitForElementPresent(LOGIN_BUTTON, "Cannot find auth button", 10);


        this.waitForElementAndClick(LOGIN_BUTTON, "Cannot find and click auth button",15);

    }
    //находим элемент оверлэй
    @Step("Поиск оверлэй")
    public WebElement overlay() {
        this.waitForElementPresent(OVERLAY, "Cannot find overlay Auth",15);
        // Находим элемент
        return this.findElement(OVERLAY);

    }



    //метод ввода данных для авторизации
    @Step("Ввод логина и пароля")
    public void enterLoginData(String login, String password){
        this.waitForElementAndSendKeys(LOGIN_INPUT, login, "Cannot find and put a login to the login input",15);
        this.waitForElementAndSendKeys(PASSWORD_INPUT, password,"Cannot find and put a password to the login input",15);
    }

    //метод, который кликает по кнопке сабмит
    @Step("Подтвердить форму ЛогИн")
    public void submitForm() {
        this.waitForElementAndClick(SUBMIT_BUTTON, "Cannot find and click submit button",15);
    }




}

