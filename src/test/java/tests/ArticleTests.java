package tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import lib.CoreTestCase;
import lib.UI.ArticlePageObject;
import lib.UI.SearchPageObject;
import lib.UI.factories.ArticlePageObjectFactory;
import lib.UI.factories.SearchPageObjectFactory;
import org.junit.Assert;
import org.junit.Test;

@Epic("Tests for articles")
public class ArticleTests extends CoreTestCase {
    //Все тесты на статьи
    //Тест3 Сравнить название статьи
    @Test
    @Features(value = {@Feature(value="Search"),@Feature(value="Article")})
    @DisplayName("Сравнение названия статьи с ожидаемым названием")
    @Description("Открываем статью Java (programming language) и убеждаемся,что заголовок соответствует ожидаемому (Java (programming language))")
    @Step("Старт testCompareArticleTitle")
    //критичность теста
    @Severity(value = SeverityLevel.BLOCKER)
    public void testCompareArticleTitle() {
        //пропустить онбординг
        this.skipOnboarding();

        //инициализация
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        //поиска строки элемента и клика
        SearchPageObject.initSearchInputAndClick();
        //поиск элемента и отправки значения в поле
        SearchPageObject.typeSearchLine("Java");
        //Поиск элемента и клик по нему
        SearchPageObject.clickByArticleWithSubstring("programming language");

        //используем новый метод. инициализация
        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        //поиск заголовка нужной статьи
        ArticlePageObject.waitForTitleElement("Java (programming language)");
        //получаем название статьи, текст этой статьи и записываем ее в переменную
        String article_title = ArticlePageObject.getArticleTitle("Java (programming language)");

        //делаем скриншот и даем имя скриншоту
        //ArticlePageObject.takeScreenshot("article_page");

        //используем это название статьи для сравнения
        Assert.assertEquals(
                "We see unexpected title",
                "Java (programming language)",
                article_title
        );

    }

    //Тест4, свайп до конца страницы до текста в футере
    @Test
    @Features(value = {@Feature(value="Search"),@Feature(value="Article")})
    @DisplayName("Свайп до конца статьи")
    @Description("Открываем статью и пролистываем ее до конца, до определенного текста в футере")
    @Step("Старт testSwipeArticle")
    @Severity(value = SeverityLevel.MINOR)
    public void testSwipeArticle() {
        //пропустить онбординг
        this.skipOnboarding();

        //инициализация
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        //поиска строки элемента и клика
        SearchPageObject.initSearchInputAndClick();
        //поиск элемента и отправки значения в поле
        SearchPageObject.typeSearchLine("Java");
        //Поиск элемента и клик по нему
        SearchPageObject.clickByArticleWithSubstring("programming language");
        //используем новый метод. инициализация
        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);;
        //поиск заголовка нужной статьи, ждем появления названия
        ArticlePageObject.waitForTitleElement("Java (programming language)");
        //swipe
        ArticlePageObject.swipeToFooter();

        //делаем скриншот и даем имя скриншоту
        //ArticlePageObject.takeScreenshot("article_footer");

    }
    //Ex6. Тест14, который открывает статью и убеждается, что у нее есть элемент title.  тест не должен
    // дожидаться появления title, проверка должна производиться сразу. Если title не найден - тест падает с ошибкой.
    @Test
    @Feature(value="Article")
    @DisplayName("Проверка, что у статьи есть название, не дожидаясь появления этого названия")
    @Description("Ищем определенную статью в поиске, находим ее заголовок и убеждаемся, что заголовок соответствует ожидаемому еще до открытия самой статьи")
    @Step("Старт testAssertTitle")
    @Severity(value = SeverityLevel.TRIVIAL)
    public void testAssertTitle() {
        //пропустить онбординг
        this.skipOnboarding();

        //инициализация
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        //поиска строки элемента и клика
        SearchPageObject.initSearchInputAndClick();
        //поиск элемента и отправки значения в поле
        SearchPageObject.typeSearchLine("Java");
        //Поиск элемента в результатах
        SearchPageObject.waitForSearchResult("Object-oriented programming language");
        //Работа с заголовком статьи. Инициализация
        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        // Проверяем, что у статьи есть элемент title
        ArticlePageObject.assertElementPresentWithSearchTitle("Java (programming language)");

    }


}
