package lib.UI;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import lib.Platform;

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
        CANCEL_BUTTON;



    //инициализация драйвера
    public ArticlePageObject(AppiumDriver driver){
        super(driver);
    }

    /*TEMPLATES METHODS */
    //метод, который подставляет подстроку по шаблону в заголовок статья
    private static String getResultTitleElement(String substring){
        //меняем значение переменной SUBSTRING на строчку substring
        return TITLE_TPL.replace("{SUBSTRING}", substring);
    }
    private static String getResultTitleSecondElement(String substring){
        //меняем значение переменной SUBSTRING на строчку substring
        return TITLE_TPL2.replace("{SUBSTRING}", substring);
    }



    /*TEMPLATES METHODS */

    //метод ожидания статьи
    public WebElement waitForTitleElement(String substring){
        String title_Element_xpath = getResultTitleElement(substring);
        return this.waitForElementPresent(title_Element_xpath,"Cannot find article title",25);

    }

    public WebElement waitForTitleSecondElement(String substring){
        String title_Element_xpath = getResultTitleSecondElement(substring);
        return this.waitForElementPresent(title_Element_xpath,"Cannot find article title",25);

    }
    //метод получение название первой статьи
    public String getArticleTitle(String substring) {
        WebElement title_element = waitForTitleElement(substring);
        //метод будет возвращать название статьи
        if (Platform.getInstance().isAndroid()){
            return title_element.getAttribute("text");
        }else {
            return title_element.getAttribute("name");
        }
    }


    //метод в котором будем получать название второй статьи
    public String getArticleSecondTitle(String substring){
        WebElement title_element = waitForTitleSecondElement(substring);
        //метод будет возвращать название статьи
        if (Platform.getInstance().isAndroid()){
            return title_element.getAttribute("text");
        }else {
            return title_element.getAttribute("name");
        }
    }


    //сделаем метод свайпа до футера
    public void swipeToFooter(){
        if(Platform.getInstance().isAndroid()) {
            this.verticalSwipeToFindElement(
                    FOOTER_ELEMENT,
                    "Cannot find the end or Article",
                    50
            );
        } else {
            this.swipeUPTitleElementAppear(FOOTER_ELEMENT,
            "Cannot find the end or Article",
            50);
        }


    }
    //метод с шагами, которые добавляют статью в список статей
    public void addArticleToMyList(String name_of_folder){
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
                "Cannot find back-button to cancel search",
                20
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
    public void comeBackToMain(){
        this.waitForElementAndClick(
                CANCEL_BUTTON,
                "Cannot find back-button to cancel search",
                20
        );
    }


    // Проверяем, что у статьи есть элемент title
    public void assertElementPresentWithSearchTitle(String substring){
        String title_Element_xpath = getResultTitleElement(substring);
        this.assertElementPresent(title_Element_xpath);
    }

    //метод добавления статьи в список для IOS
    public void addArticlesToMySaved(){
        this.waitForElementAndClick(OPTIONS_ADD_TO_MY_LIST_BUTTON,"Cannot find option to add article to reading list", 15);

    }




}
