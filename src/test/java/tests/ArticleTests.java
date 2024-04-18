package tests;

import lib.CoreTestCase;
import lib.UI.ArticlePageObject;
import lib.UI.SearchPageObject;
import lib.UI.factories.ArticlePageObjectFactory;
import lib.UI.factories.SearchPageObjectFactory;
import org.junit.Test;

public class ArticleTests extends CoreTestCase {
    //Все тесты на статьи
    //Тест3 Сравнить название статьи
    @Test
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
        SearchPageObject.clickByArticleWithSubstring("Java (programming language)");

        //используем новый метод. инициализация
        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        //поиск заголовка нужной статьи
        ArticlePageObject.waitForTitleElement("Java (programming language)");
        //получаем название статьи, текст этой статьи и записываем ее в переменную
        String article_title = ArticlePageObject.getArticleTitle("Java (programming language)");

        //используем это название статьи для сравнения
        assertEquals(
                "We see unexpected title",
                "Java (programming language)",
                article_title
        );

    }

    //Тест4, свайп до конца страницы до текста в футере
    @Test
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
        SearchPageObject.clickByArticleWithSubstring("Java (programming language)");
        //используем новый метод. инициализация
        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);;
        //поиск заголовка нужной статьи, ждем появления названия
        ArticlePageObject.waitForTitleElement("Java (programming language)");
        //swipe
        ArticlePageObject.swipeToFooter();

    }
    //Ex6. Тест14, который открывает статью и убеждается, что у нее есть элемент title.  тест не должен
    // дожидаться появления title, проверка должна производиться сразу. Если title не найден - тест падает с ошибкой.
    @Test
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
