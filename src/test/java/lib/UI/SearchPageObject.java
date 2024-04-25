package lib.UI;

import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Step;
import lib.Platform;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//методы, которые будут использоваться для поиска
//этот метод наследуется от MainPageObject
abstract public class SearchPageObject extends  MainPageObject {
    //задаем константы. То, что всегда знаем, как выглядит. Например, поиск на странице. Он не изменен, его можно задать константой
    //или локатор кнопки, которая скипает онбординг


    protected static String SEARCH_INPUT;
    protected static String SEARCH_INPUT_ELEMENT;
    protected static String SEARCH_RESULT_BY_SUBSTRING_TPL;
    protected static String SEARCH_RESULTS_TITLE_TPL;
    protected static String SEARCH_RESULTS_DESCRIPTION_TPL;
    protected static String SEARCH_RESULT_TITLE_AND_DESCRIPTION_TPL;
    protected static String SEARCH_CANCEL_BUTTON ;
    protected static String SEARCH_CLOSE_BUTTON;
    protected static String SEARCH_RESULT_ELEMENT;
    protected static String SEARCH_EMPTY_RESULT_ELEMENT;
    protected static String RESULT_LIST;
    protected static String EMPTY_RESULT_LIST;

    //инициализируем драйвер
    public SearchPageObject(RemoteWebDriver driver){
        //берем дравер из MainPageObject
        super(driver);
    }


    /*TEMPLATES METHODS */
    //метод, который подставляет подстроку по шаблону
    @Step("Подставляем в элемент определенное значение подстроки")
    private static String getResultSearchElement(String substring){
        //меняем значение переменной SUBSTRING на строчку substring
        String Elements_xpath = SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
        return String.format("%s",Elements_xpath);
    }
    @Step("Подставляем в название статьи определенное значение, подставляем в описание статьи определенное значение")
    private static String getResultTitleAndDescriptionElements(String title, String description){

        //меняем значение переменной SUBSTRING на строчку substring
        String title_Elements_xpath = SEARCH_RESULTS_TITLE_TPL.replace("{SUBSTRING_TITLE}", title);
        String description_Elements_xpath = SEARCH_RESULTS_DESCRIPTION_TPL.replace("{SUBSTRING_DESCRIPTION}", description);
        return String.format("(%s)[%s]", title_Elements_xpath, description_Elements_xpath);

    }

    private static String getArticleWithTitleAndDescription(String title, String description) {
        return SEARCH_RESULT_TITLE_AND_DESCRIPTION_TPL
                .replace("{SUBSTRING_TITLE}", title)
                .replace("{SUBSTRING_DESCRIPTION}", description);
    }


    //метод получение название первой статьи
    @Step("Получаем название статьи и описание")
    public String getArticleByTitleAndDescription(String title, String description) {
        WebElement title_element = waitForElementByTitleAndDescription(title, description);
        //метод будет возвращать название статьи
        if (Platform.getInstance().isAndroid()){
            return title_element.getAttribute("text");
        }else {
            return title_element.getAttribute("name");
        }
    }


    /*TEMPLATES METHODS */

    @Step("Ожидаем элемент веб-страницы, соответствующий заданному заголовку и описанию")
    public WebElement waitForElementByTitleAndDescription(String title, String description) {

        String title_description_Elements_xpath = getResultTitleAndDescriptionElements(title,description);
        //return this.waitForElementPresent(String.valueOf(By.xpath(title_description_Elements_xpath)), "Cannot find article title and description", 30);
        By by = By.xpath(title_description_Elements_xpath);

        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.withMessage("Cannot find article title and description");

        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );

    }

    //метод, который возвращает список элементов веб-страницы, соответствующих заданному заголовку и описанию.
    @Step("Возвращаем список элементов веб-страницы, соответствующих заданному заголовку и описанию")
    public List<WebElement> findElementsByTitleAndDescription(String title, String description) {
        String title_Elements_xpath = SEARCH_RESULTS_TITLE_TPL.replace("{SUBSTRING_TITLE}", title);
        String description_Elements_xpath = SEARCH_RESULTS_DESCRIPTION_TPL.replace("{SUBSTRING_DESCRIPTION}", description);

        By title_by = By.xpath(title_Elements_xpath);
        By description_by = By.xpath(description_Elements_xpath);

        List<WebElement> title_elements = driver.findElements(title_by);
        List<WebElement> description_elements = driver.findElements(description_by);

        List<WebElement> intersection = new ArrayList<>();
        for (WebElement title_element : title_elements) {
            if (description_elements.contains(title_element)) {
                intersection.add(title_element);
            }
        }

        return intersection;
    }


    @Step("Инициализируем поле поиска")
    public WebElement initSearchInput() {
        this.waitForElementPresent(SEARCH_INPUT_ELEMENT, "Cannot find search input after clicking search init element",15);
        // Находим элемент
        WebElement element = driver.findElement(By.xpath(SEARCH_INPUT_ELEMENT));
        // Возвращаем найденный элемент
        return element;
    }
    @Step("Кликаем в поле поиска")
    public void initSearchInputAndClick() {
        this.waitForElementPresent(SEARCH_INPUT, "Cannot find search input after clicking search init element",15);
        this.waitForElementAndClick(SEARCH_INPUT, "Cannot find and click search init element", 15);
    }
    public void initSearchButtonAndClick(){
        this.waitForElementPresent(SEARCH_INPUT_ELEMENT, "Cannot find search input after clicking search init element",15);
        this.waitForElementAndClick(SEARCH_INPUT_ELEMENT, "Cannot find and click search init element", 15);
    }
    @Step("Ввод '{search_line}' в поле поиска")
    public void typeSearchLine(String search_line){
        this.waitForElementAndSendKeys(SEARCH_INPUT_ELEMENT, search_line, "Cannot find and type into search input",15);
    }
    @Step("Ожидаем появление результатов поиска")
    public void waitForSearchResult (String substring){
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementPresent(search_result_xpath,"Cannot find search result with substring " +substring);
        System.out.println("Нашел результат поиска " +substring);
    }
    @Step("Клик по статье с нужным названием и описанием")
    public void clickByArticleWithSubstring (String substring){
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementAndClick(search_result_xpath,"Cannot find and click search result with substring" +substring, 15);
    }
    //метод, который будет искать пустой список результатов
    @Step("Ожидаем появления пустого списка результатов")
    public void waitForEmptyResultsList(){
        this.waitForElementPresent(EMPTY_RESULT_LIST, "There are several articles in the results list",15);
        // вывести в консоль
        System.out.println("Results list is empty");
    }

    //метод, который будет искать кнопку возврата
    @Step("Ожидаем кнопку возврата в поиске")
    public void waitForCancelButtonToAppear(){
        this.waitForElementPresent(SEARCH_CANCEL_BUTTON, "Cannot find search cancel button",15);
    }
    //метод, который будет ожидать отсутствие этой кнопки по окончанию теста
    @Step("Ожидаем отсутствие кнопки возврата в  поиске")
    public void waitForCancelButtonToDisappear(){
        this.waitForElementNotPresent(SEARCH_CANCEL_BUTTON, "Search cancel button is still present",15);
    }
    //клик по кнопку возврата
    @Step("Клик по кнопке возврата в поле поиска")
    public void clickCancelSearch(){
        this.waitForElementAndClick(SEARCH_CANCEL_BUTTON, "Cannot find and click search cancel button",15);
    }
    //клик по кнопку закрыть
    @Step("Клик по кнопке закрытия в поиске")
    public void clickCloseSearch(){
        this.waitForElementAndClick(SEARCH_CLOSE_BUTTON, "Cannot find and click search close button",15);
    }



    //метод, который выдает кол-во статей
    @Step("Определяем количество найденных статей")
    public int getAmountOfFoundArticles(){
        //поиск элемента
        this.waitForElementPresent(
                SEARCH_RESULT_ELEMENT,
                "Cannot find anything by the request ",
                15
        );
        //метод возвращал нам кол-во элементов по определенному xpath
        return this.getAmountOfElements(SEARCH_RESULT_ELEMENT);

    }

    //находим элемент 'лист результатов' поиска
    @Step("Находим лист всех результатов поиска")
    public WebElement resultsList() {
        this.waitForElementPresent(RESULT_LIST, "Cannot find search resultList",15);
        // Находим элемент
        return this.findElement(RESULT_LIST);

    }

    //счетчик результатов поиска
    @Step("Определяем количество всех найденных статей в листе результатов поиска")
    public int getSearchResultsCount(int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        List<WebElement> elements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(SEARCH_RESULT_ELEMENT)));
        return elements.size();
    }


    //подтверждаем, что на странице нет результатов
    @Step("Ожидаем появление лэйбла пустого результата поиска")
    public void waitForEmptyResultsLabel(){
        //поиск элемента
        this.waitForElementPresent(SEARCH_EMPTY_RESULT_ELEMENT,"Cannot find empty result element", 15);

    }
    @Step("Проверяем, что на странице нет результатов поиска  со значением '{search_line}'")
    public void assertThereIsNoResultOfSearch(String search_line){
        //подтверждаем, что на странице нет результатов
        this.assertNoElementsPresentWithText(SEARCH_RESULT_ELEMENT, search_line, "We've found some results by request" + search_line);

    }
    @Step("Проверяем, что в поле поиска есть ожидаемый текст '{expectedText}'")
    public void assertThereIsTextInSearchInput(WebElement element, String expectedText){
        //подтверждаем, что в поле поиска есть текст
        this.assertElementHasText(element, expectedText);
    }
    @Step("Проверяем, что в результате поиска есть ожидаемый текст '{expectedText}'")
    public void assertSearchResultsWithText(WebElement element, String expectedText){
        //подтверждаем, что в поле поиска есть текст
        this.assertMultipleSearchResultsWithText(SEARCH_RESULT_ELEMENT, element, expectedText);
    }


}
