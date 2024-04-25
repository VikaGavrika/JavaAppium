package lib.UI;

import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import lib.Platform;
import org.openqa.selenium.remote.RemoteWebDriver;

import javax.management.StringValueExp;

abstract public class ArticlePageObject extends MainPageObject{
    protected static String
        TITLE_TPL,
        TITLE_TPL2,
        FOOTER_ELEMENT,
        OPTIONS_BUTTON,
        TOOLBAR_BUTTON,
        NAVIGATE_BUTTON,
        SAVE_BUTTON,
        OPTIONS_ADD_TO_MY_LIST_BUTTON,
        MY_LIST_NAME_INPUT,
        MY_LIST_OK_BUTTON,
        CLOSE_ARTICLE_BUTTON,
        OPTIONS_VIEW_LIST_BUTTON,
        OPTIONS_REMOVE_FROM_MY_LIST_BUTTON,
        CANCEL_BUTTON;



    //инициализация драйвера
    public ArticlePageObject(RemoteWebDriver driver){
        super(driver);
    }

    /*TEMPLATES METHODS */
    //метод, который подставляет подстроку по шаблону в заголовок статья
    @Step("Подставляет подстроку '{substring}' в заголовок первой статьи")
    private static String getResultTitleElement(String substring){
        //меняем значение переменной SUBSTRING на строчку substring
        return TITLE_TPL.replace("{SUBSTRING}", substring);
    }
    @Step("Подставляет подстроку '{substring}' в заголовок второй статьи")
    private static String getResultTitleSecondElement(String substring){
        //меняем значение переменной SUBSTRING на строчку substring
        return TITLE_TPL2.replace("{SUBSTRING}", substring);
    }



    /*TEMPLATES METHODS */

    //метод ожидания статьи
    @Step("Ожидаем название '{substring}' на странице статьи")
    public WebElement waitForTitleElement(String substring){
        String title_Element_xpath = getResultTitleElement(substring);
        return this.waitForElementPresent(title_Element_xpath,"Cannot find article title",25);

    }
    @Step("Ожидаем название второй статьи '{substring}' на странице статьи")
    public WebElement waitForTitleSecondElement(String substring){
        String title_Element_xpath = getResultTitleSecondElement(substring);
        return this.waitForElementPresent(title_Element_xpath,"Cannot find article title",25);

    }
    //метод получение название первой статьи
    @Step("Получаем название '{substring}' на странице статьи")
    public String getArticleTitle(String substring) {
        WebElement title_element = waitForTitleElement(substring);
        //метод будет возвращать название статьи
        if (Platform.getInstance().isAndroid()){
            return title_element.getAttribute("text");
        }else if (Platform.getInstance().isIOS()){
            return title_element.getAttribute("name");
        }else {
            return title_element.getText();

        }
    }


    //метод в котором будем получать название второй статьи
    @Step("Получаем название второй статьи '{substring}' на странице статьи")
    public String getArticleSecondTitle(String substring){
        WebElement title_element = waitForTitleSecondElement(substring);
        //метод будет возвращать название статьи
        if (Platform.getInstance().isAndroid()){
            return title_element.getAttribute("text");
        }else if (Platform.getInstance().isIOS()){
            return title_element.getAttribute("name");
        }else {
            return title_element.getText();
        }
    }


    //сделаем метод свайпа до футера
    @Step("Делаем свайп до футера на странице статьи")
    public void swipeToFooter(){
        if(Platform.getInstance().isAndroid()) {
            this.verticalSwipeToFindElement(
                    FOOTER_ELEMENT,
                    "Cannot find the end or Article",
                    50
            );
        } else if (Platform.getInstance().isIOS()){
            this.swipeUPTitleElementAppear(FOOTER_ELEMENT,
            "Cannot find the end or Article",
            50);
        }else {
            this.scrollWebPageTitleElementNotVisible(
                    FOOTER_ELEMENT,
                    "Cannot find the end or Article",
                    40
            );
        }


    }
    //метод с шагами, которые добавляют статью в список статей
    @Step("Добавляем статью в список статей")
    public void addArticleToMyList(String name_of_folder) {
        //делаем переменные для каждого из элемента, так их будет проще менять


        //нажать на кнопку с выпадающим списком
        this.waitForElementAndClick(
                OPTIONS_BUTTON,
                "Cannot find button to open article options",
                20
        );
        //нажать на кнопку настроек тулбара
        this.waitForElementAndClick(
                TOOLBAR_BUTTON,
                "Cannot find button to open customize_toolbar",
                20
        );

        //перенос кнопки элемента по координатам
        this.moveButton (200,1010,693,1005,1748);

        //возврат к статье, нажав Назад
        this.waitForElementAndClick(
            NAVIGATE_BUTTON,
            "Cannot find back-button to cancel search",20
        );
        //снова нажать на кнопку с выпадающим списком
        this.waitForElementAndClick(
                OPTIONS_BUTTON,
                "Cannot find button to open article options",
                20
        );
        //нажать на кнопку Save в выпадающем списке
        this.waitForElementAndClick(
                SAVE_BUTTON,
                "Cannot find options to add article to reading list",
                20
        );
        //в появившимся снэк-баре нажать кнопку добавления в список
        this.waitForElementAndClick(
                OPTIONS_ADD_TO_MY_LIST_BUTTON,
                "Cannot find button Add to list",
                20
        );

        //ввести название в поле ввода
        this.waitForElementAndSendKeys(
                MY_LIST_NAME_INPUT,
                name_of_folder,
                "Cannot put text into articles folder input",
                20
        );
        //нажать на кнопку ОК
        this.waitForElementAndClick(
                MY_LIST_OK_BUTTON,
                "Cannot press ОК button",
                20
        );
        //нажать на кнопку в снэк баре View list
        this.waitForElementAndClick(
                OPTIONS_VIEW_LIST_BUTTON,
                "Cannot press View list button",
                20
        );


    }

    //метод с шагами, который добавляет еще одну статью в список
    @Step("Добавляем вторую статью в список статей")
    public void addSecondArticleToMyList(String name_of_folder){
        //нажать на кнопку с выпадающим списком
        this.waitForElementAndClick(
                OPTIONS_BUTTON,
                "Cannot find button to open article options",
                20
        );
        //нажать на кнопку Save в выпадающем списке
        this.waitForElementAndClick(
                SAVE_BUTTON,
                "Cannot find options to add article to reading list",
                20
        );
        //в появившимся снэк-баре нажать кнопку добавления в список
        this.waitForElementAndClick(
                OPTIONS_ADD_TO_MY_LIST_BUTTON,
                "Cannot find button Add to list",
                20
        );
        //в открывшемся батоншите списков найти нужный список и кликнуть
        this.waitForElementAndClick(
                "xpath://*[@text='"+name_of_folder+"']",
                "Cannot find folder articles into My list",
                20
        );
        //нажать на кнопку в снэк баре View list
        this.waitForElementAndClick(
                OPTIONS_VIEW_LIST_BUTTON,
                "Cannot press View list button",
                20
        );

    }
    //метод закрытия для разных платформ
    @Step("Закрываем статью")
    public void closeArticle(){
        if(Platform.getInstance().isAndroid()) {
            this.waitForElementAndClick(
                    NAVIGATE_BUTTON,
                    "Cannot find back-button to cancel search",
                    20
            );
        }else {
            this.waitForElementAndClick(
                    CLOSE_ARTICLE_BUTTON,
                    "Cannot find back-button to cancel search",
                    20
            );
        }
    }
    //метод возврата на главную для Айос
    @Step("Возвращаемся на Главную")
    public void comeBackToMain(){
        this.waitForElementAndClick(
                CANCEL_BUTTON,
                "Cannot find back-button to cancel search",
                20
        );
    }


    // Проверяем, что у статьи есть элемент title
    @Step("Проверяем, что у статьи есть название '{substring}'")
    public void assertElementPresentWithSearchTitle(String substring){
        String title_Element_xpath = getResultTitleElement(substring);
        this.assertElementPresent(title_Element_xpath);
    }

    //метод добавления статьи в список для IOS
    @Step("Добавляем статью в список для IOS")
    public void addArticlesToMySaved(){
        //только для вэба сначала удаляем статью из сохраненок,если она там есть.
        if (Platform.getInstance().isMW()){
            this.removeArticleFromSavedIfItAdded();
        }
        //для Айос просто сохраняем статью
        this.waitForElementAndClick(OPTIONS_ADD_TO_MY_LIST_BUTTON,"Cannot find option to add article to reading list", 15);

    }
    //метод, который будет удалять статью,если она уже была в избранном
    //если кнопка удаления статьи из избранного присутствует мы кликаем по кнопке удаления
    @Step("Удаляем статью, потому что она уже была в Избранном")
    public void removeArticleFromSavedIfItAdded() {
        if (this.isElementPresent(OPTIONS_REMOVE_FROM_MY_LIST_BUTTON)) {
            this.waitForElementAndClick(
                    OPTIONS_REMOVE_FROM_MY_LIST_BUTTON,
                    "Cannot click button to remove an article from saved",
                    10
            );
            //после того,как удалили статью проверяем,что кнопка добавления статьи появилась.
            this.waitForElementPresent(
                    OPTIONS_ADD_TO_MY_LIST_BUTTON,
                    "Cannot find button to add an article to saved list after removing it from list before",
                    10
            );
        }
    }




}
