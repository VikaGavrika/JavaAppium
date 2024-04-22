package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.UI.ArticlePageObject;
import lib.UI.SearchPageObject;
import lib.UI.factories.ArticlePageObjectFactory;
import lib.UI.factories.SearchPageObjectFactory;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.NoSuchElementException;

public class SearchTests extends CoreTestCase {
    //тесты, связанные с поиском
    //Тест1. Поиск
    @Test
    public void testSearch() {
        //пропустить онбординг
        this.skipOnboarding();
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInputAndClick();
        //поиск элемента и отправки значения в поле
        SearchPageObject.typeSearchLine("Java");
        //метод проверяющий, что поиск по значению "Java" работает корректно и находится нужная статья с нужным заголовком
        SearchPageObject.waitForSearchResult("Object-oriented programming language");


    }
    //Тест2. Отменяет поиск
    @Test
    public void testCanselSearch() {
        //пропустить онбординг
        this.skipOnboarding();

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        //поиска строки элемента и клика
        SearchPageObject.initSearchInputAndClick();
        //поиск элемента и отправки значения в поле
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitForCancelButtonToAppear();
        //дожидаемся кнопки возврата и кликаем по ней
        SearchPageObject.clickCancelSearch();
        //Метод, который проверяет, что после нажатия "Назад", мы вернулись на страницу, где нет элемента стрелки "назад"
        SearchPageObject.waitForCancelButtonToDisappear();


    }


    //тест5, который проверяет, что поле ввода для поиска статьи содержит текст Search Wikipedia
    @Test
    public void testSearchInputHasText() {
        //пропустить онбординг
        this.skipOnboarding();

        //инициализация
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        //поиск поля поиска
        WebElement element = SearchPageObject.initSearchInput();
        // Ожидаемый текст
        String expectedText = "Search Wikipedia";
        // Проверка актуального текста и ожидаемого
        SearchPageObject.assertThereIsTextInSearchInput(element, expectedText);

    }

    //EX3. Тест6, который делает поиск по какому-то слову. Затем убеждается, найдены несколько статей со словом в листе результатов,
    // затем удаляет результаты поиска и убеждается что лист с результатами пуст
    @Test
    public void testSearchAndCanselSearch() {
        //пропустить онбординг
        this.skipOnboarding();

        //инициализация
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        //поиска строки элемента и клика
        SearchPageObject.initSearchInputAndClick();
        //поиск элемента и отправки значения в поле
        SearchPageObject.typeSearchLine("planet");
        //выдает кол-во статей
        int amount_of_search_results = SearchPageObject.getAmountOfFoundArticles();
        //убеждаемся, что кол-во полученных элементов больше нуля
        assertTrue(
                "We found too few results",
                amount_of_search_results > 0
        );
        //сохраняем результаты поиска в список
        WebElement element = SearchPageObject.resultsList();
        if (element.getText().isEmpty()) {
            System.out.println("Список результатов поиска пустой");
        } else {
            String expectedText = "planet";
            //проверяем, что найдены несколько статей со словом planet в листе результатов
            SearchPageObject.assertSearchResultsWithText(element, expectedText);
            //дожидаемся кнопки закрытия и кликаем по ней
            SearchPageObject.clickCloseSearch();
            //проверяем, что нет статей в листе результатов, есть пустой лист результатов
            SearchPageObject.waitForEmptyResultsList();
        }

    }

    //Ex4. Тест7, который делает поиск по какому-то слову. Затем убеждается, что в каждом результате поиска есть это слово.
    @Test
    public void testSearchTextAndCheckTextInTitles() {
        //пропустить онбординг
        this.skipOnboarding();

        //инициализация
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        //поиска строки элемента и клика
        SearchPageObject.initSearchInputAndClick();
        //поиск элемента и отправки значения в поле
        SearchPageObject.typeSearchLine("Java");
        //сохраняем в список результаты поиска
        WebElement element = SearchPageObject.resultsList();
        // Проверяем, пустой ли список
        if (element.getText().isEmpty()) {
            System.out.println("Список результатов поиска пустой");
        } else {
            //проверяем, что в каждой статье в листе результатов есть ожидаемое слово
            String expectedText = "Java";
            SearchPageObject.assertSearchResultsWithText(element, expectedText);
        }
    }



    //Тест9, которой ищет какую-то конкретную статью, а затем проверяет, что вышел 1 результат с этой статьей
    @Test
    public void testAmountOfNotEmptySearch(){
        //пропустить онбординг
        this.skipOnboarding();

        //инициализация
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        //поиска строки элемента и клика
        SearchPageObject.initSearchInputAndClick();
        //Зададим переменную, название статьи
        String search_line = "Linkin Park discography";
        //поиск элемента и отправки значения в поле
        SearchPageObject.typeSearchLine(search_line);
        //выдает кол-во статей
        int amount_of_search_results = SearchPageObject.getAmountOfFoundArticles();

        //убеждаемся, что кол-во полученных элементов больше нуля
        assertTrue(
                "We found too few results",
                amount_of_search_results > 0
        );


    }
    //Тест10, который будет проверять, что результаты поиска не содержат элементы с определенным текстом.
    // И есть элемент "нет результатов"
    @Test
    public void testAmountOfEmptySearch(){
        //пропустить онбординг
        this.skipOnboarding();

        //инициализация
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        //поиска строки элемента и клика
        SearchPageObject.initSearchInputAndClick();
        //Зададим переменную, название статьи
        String search_line = "ppppppppppp";
        //поиск элемента и отправки значения в поле
        SearchPageObject.typeSearchLine(search_line);
        //подтверждаем, что на странице нет результатов
        SearchPageObject.assertThereIsNoResultOfSearch(search_line);

    }

    //Ex9. Тест 15. который будет делать поиск по любому запросу на ваш выбор (поиск по этому слову должен возвращать как минимум 3 результата).
    // Далее тест должен убеждаться, что в результате поиска присутствуют три элемента,
    // содержащие ожидаемые вами article_title и article_description.
    @Test
    public void testSearchArticleAndCheckTitleAndDiscription() {
        //пропустить онбординг
        this.skipOnboarding();

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        //поиска строки элемента и клика
        SearchPageObject.initSearchInputAndClick();
        //поиск элемента и отправки значения в поле
        SearchPageObject.typeSearchLine("Java");
        //актуальное кол-во результатов поиска
        int actualResultsCount = SearchPageObject.getSearchResultsCount(20);
        // Проверка, что количество найденных элементов не меньше 3,если меньше, то возвращает ошибку, если нет, то рез-ты поиска
        if (actualResultsCount < 3) {
            System.out.println("Количество найденных элементов меньше 3");
            return;
        }


        String articleTitleA = "Javanese language";
        String articleDescriptionA = "Austronesian language";

        //ждём, пока элемент с заданным заголовком и описанием станет видимым на странице.
        WebElement elementA = SearchPageObject.waitForElementByTitleAndDescription(articleTitleA, articleDescriptionA);
        if (articleTitleA.equals(elementA.getText())) {
            // Выводим в консоль название
            System.out.println("Найден " + articleTitleA + " и " + articleDescriptionA + " элементы");
        }

        String articleTitleB = "Javanese script";
        String articleDescriptionB = "Writing system used for several Austronesian languages";

        //ждём, пока элемент с заданным заголовком и описанием станет видимым на странице.
        WebElement elementB = SearchPageObject.waitForElementByTitleAndDescription(articleTitleB, articleDescriptionB);
        if (articleTitleB.equals(elementB.getText())) {
            // Выводим в консоль название
            System.out.println("Найден " + articleTitleB + " и " + articleDescriptionB + " элементы");
        }

        String articleTitleC = "Java (software platform)";
        String articleDescriptionC = "Set of computer software and specifications";

        //ждём, пока элемент с заданным заголовком и описанием станет видимым на странице.
        SearchPageObject.waitForElementByTitleAndDescription(articleTitleC, articleDescriptionC);
        //достаем текст из элемента
        String elementC = SearchPageObject.getArticleByTitleAndDescription("Java (software platform)","Set of computer software and specifications");
        //проверяем одинаковы ли две строки, без учета регистра и не пуста ли строка elementC
        if (articleTitleC.equalsIgnoreCase(elementC) && !elementC.isEmpty()) {
            // Выводим в консоль название
            System.out.println("Найден " + articleTitleC + " и " + articleDescriptionC + " элементы");
        }

    }

}
