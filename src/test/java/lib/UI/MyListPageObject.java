package lib.UI;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static java.awt.AWTEventMulticaster.add;

abstract public class MyListPageObject extends MainPageObject {
    protected static String
            FOLDER_BY_NAME_TPL,
            ARTICLE_BY_TITLE_TPL,
            ARTICLE_BY_DESCRIPTION_TPL,
            DESCRIPTION,
            CLOSE_MODAL_WINDOW_BUTTON,
            ARTICLE_CONTAINER,
            REMOVE_FROM_SAVED_BUTTON,
            DELETE_BUTTON;


    private static String getFolderXpathByName(String name_of_folder) {
        return FOLDER_BY_NAME_TPL.replace("{FOLDER_NAME}", name_of_folder);
    }

    private static String getRemoveButtonByTitle(String article_title) {
        return REMOVE_FROM_SAVED_BUTTON.replace("{TITLE}", article_title);
    }
    private static String getSavedArticleXpathByName(String article_title) {
        return ARTICLE_BY_TITLE_TPL.replace("{TITLE}", article_title);
    }

    private static String getResultDescriptionElement(String article_description) {
        //меняем значение переменной SUBSTRING на строчку substring
        return ARTICLE_BY_DESCRIPTION_TPL.replace("{DESCRIPTION}", article_description);

    }



    //метод ожидания описания
    public WebElement waitForDescriptionElement(String article_description) {
        String description_Element_xpath = getResultDescriptionElement(article_description);
        return this.waitForElementPresent(description_Element_xpath, "Cannot find article description " + article_description, 25);

    }

    //метод в котором будем получать название статьи
    public String getArticleDescription(String article_description) {
        WebElement description_element = waitForDescriptionElement(article_description);
        //метод будет возвращать название статьи
        if (Platform.getInstance().isAndroid()) {
            return description_element.getAttribute("text");
        } else {
            return description_element.getAttribute("name");
        }

    }

    //инициализ драйвер
    public MyListPageObject(RemoteWebDriver driver) {
        super(driver);
    }

    //для Айос. метод закрывающий мод окно, возникшее на экране списков сохр статей
    public void close_modal_window() {
        this.waitForElementAndClick(
                CLOSE_MODAL_WINDOW_BUTTON,
                "Cannot find close modal window button to cancel search",
                20
        );
    }

    //поиск папки с именем и открываем ее
    public void openFolderByName(String name_of_folder) {
        String folder_name_xpath = getFolderXpathByName(name_of_folder);
        //поиск списка статей по названию, название задано в переменную выше. клик на список статей
        this.waitForElementAndClick(
                folder_name_xpath,
                "Cannot find folder by name" + name_of_folder,
                20
        );
    }

    //Метод ожидания статьи
    public void waitForArticleToAppearByTitle(String article_title) {
        String article_xpath = getSavedArticleXpathByName(article_title);
        //поиск списка статей по названию
        this.waitForElementPresent(
                article_xpath,
                "Cannot find saved article by title" + article_title,
                20
        );
    }

    public void waitForArticleToAppearByTitleAndClick(String article_title) {
        String article_xpath = getSavedArticleXpathByName(article_title);
        //поиск списка статей по названию
        this.waitForElementPresent(
                article_xpath,
                "Cannot find saved article by title" + article_title,
                20
        );
        this.waitForElementAndClick(
                article_xpath,
                "Cannot find saved article by title" + article_title,
                20

        );
    }

    public void swipeByArticleToDelete(String article_title) {
        //находится статья на экране
        this.waitForArticleToAppearByTitle(article_title);
        //удаление статьи свайпом влево
        this.leftSwipe(200, 826, 977, 92, 941);
    }

    public void swipeByArticleToDeleteFromList(String article_title) {
        //находится статья на экране
        this.waitForArticleToAppearByTitle(article_title);
        //удаление статьи свайпом влево
        this.leftSwipe(200, 826, 977, 92, 941);

    }

    //для Айос
    public void swipeByArticleToDeleteFromIOSList(String article_title) {
        //находится статья на экране
        this.waitForArticleToAppearByTitle(article_title);
        //получаем элемент статьи
        WebElement element = waitForElementPresent(ARTICLE_CONTAINER, "Cannot find article container", 15);
        //вычисление offsetX
        int elementX = element.getSize().getWidth();
        int offsetX = elementX / 2;
        //удаление статьи свайпом влево
        this.leftSwipeWithOffsetX(200, elementX, offsetX, 277, 277);
        //клик по корзинке удаления
        this.waitForElementAndClick(DELETE_BUTTON, "Cannot find delete action button", 15);
        System.out.println("Статья удалена...");


    }
    public void deleteArticleFromMWList(String article_title)throws InterruptedException{
        System.out.println("начинаю удаление статьи.");
        String remove_locator = getRemoveButtonByTitle(article_title);
        Thread.sleep(5000);
        this.waitForElementAndClick(
                remove_locator,
                "Cannot click button to remove article from saved",
                10
        );
        Thread.sleep(5000);
        System.out.println("начинаю обновление страницы.");
        //обновление страницы, чтобы удаленная статья исчезла
        driver.navigate().refresh();
    }

    public void waitForArticleToDisappearByTitle(String article_title) {
        String article_xpath = getSavedArticleXpathByName(article_title);
        //поиск списка статей по названию
        this.waitForElementNotPresent(
                article_xpath,
                "Saved article still present" + article_title,
                20
        );
    }

    //счетчик результатов поиска
    public int getSavedArticleCount(int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        List<WebElement> elements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(ARTICLE_CONTAINER)));
        System.out.println("Найдено " + elements.size() + " сохраненных статей.");
        return elements.size();

    }



    //метод для вывода списка description найденных статей
    public List<String> getSavedArticleDescriptions() {
        List<String> articleDescriptions = new ArrayList<>();
        try {
            List<WebElement> elements = driver.findElements(By.xpath("//XCUIElementTypeStaticText"));
            // Проверка размера списка
            if (elements.size() > 0) {
                for (WebElement element : elements) {
                    // Вывод в консоль информации об элементе
                    System.out.println(element.toString());
                    if (Platform.getInstance().isAndroid()) {
                        articleDescriptions.add(element.getAttribute("text"));
                    } else {
                        articleDescriptions.add(element.getAttribute("name"));
                    }
                }
            } else {
                System.out.println("Элементы с описанием статьи не найдены");
            }
        } catch (Exception e) {
            // Обработка исключения
            System.out.println("Error getting saved article description" + e.getMessage());
        }
        return articleDescriptions;
    }
}




