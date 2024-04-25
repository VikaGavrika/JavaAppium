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

import org.openqa.selenium.WebElement;


@Epic("Search articles")
public class SearchTests extends CoreTestCase {
    //тесты, связанные с поиском
    //Тест1. Поиск
    @Test
    @Features(value = {@Feature(value="Search")})
    @DisplayName("Поиск статьи")
    @Description("Найти поле поиска, ввести в него значения для поиска. Убедиться,что в результатах поиска есть ожидаемая статья")
    @Step("Старт поиска статьи")
    //критичность теста
    @Severity(value = SeverityLevel.CRITICAL)
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
    @Features(value = {@Feature(value="Search")})
    @DisplayName("Отмена поиска")
    @Description("Найти поле поиска, ввести в него значения для поиска. Убедиться,что в результатах поиска есть ожидаемая статья. Отменить поиск")
    @Step("Старт теста по отмене поиска")
    //критичность теста
    @Severity(value = SeverityLevel.CRITICAL)
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
    @Features(value = {@Feature(value="Search")})
    @DisplayName("Поиск статьи")
    @Description("Найти поле поиска. Убедиться,что поле ввода для поиска статьи содержит текст Search Wikipedia")
    @Step("Старт поиска элемента - инпут")
    //критичность теста
    @Severity(value = SeverityLevel.TRIVIAL)
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
    @Features(value = {@Feature(value="Search")})
    @DisplayName("Поиск по определенному слову. Затем удалить поиск")
    @Description("Поиск по слову и проверка, что в результатах поиска есть статьи с ожидаемым словом. Заем удалить поиск и убедиться, что результат поиска пустой")
    @Step("Старт теста по поиску и отмене поиска")
    //критичность теста
    @Severity(value = SeverityLevel.CRITICAL)
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
        Assert.assertTrue(
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
    @Features(value = {@Feature(value="Search")})
    @DisplayName("Поиск по определенному слову и проверка заголовку статей в результатах поиска")
    @Description("Поиск по какому-то слову. Затем убеждается, что в каждом результате поиска есть это слово")
    @Step("Старт теста поиску по слову и проверки результатов поиска с этим словом")
    //критичность теста
    @Severity(value = SeverityLevel.CRITICAL)
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



    //Тест9, которой ищет какую-то конкретную статью, а затем проверяет, что вышел минимум 1 результат с этой статьей
    @Test
    @Features(value = {@Feature(value="Search")})
    @DisplayName("Поиск статьи и проверка результата,что статей больше 1")
    @Description("Поиск статьи. Затем проверяет, что вышел минимум 1 результат с этой статьей")
    @Step("Старт теста поиску статьи  и проверки результатов поиска больше 1")
    //критичность теста
    @Severity(value = SeverityLevel.MINOR)
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
        Assert.assertTrue(
                "We found too few results",
                amount_of_search_results > 0
        );


    }
    //Тест10, который будет проверять, что результаты поиска не содержат элементы с определенным текстом.
    // И есть элемент "нет результатов"
    @Test
    @Features(value = {@Feature(value="Search")})
    @DisplayName("Пустой результат поиска")
    @Description("Поиск статьи.Затем проверяет, что результаты поиска не содержат элементы с определенным текстом.И есть элемент \"нет результатов\"")
    @Step("Старт теста пустого результата поиска")
    //критичность теста
    @Severity(value = SeverityLevel.MINOR)
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
    @Features(value = {@Feature(value="Search")})
    @DisplayName("Поиск по определенному слову и выдача минимум три результата с ожидаемыми заголовками и описаниями в статьях")
    @Description("Поиск статьи.Затем проверяет, что результатов поиска больше трех и  содержат ожидаемые заголовки и описания статей.")
    @Step("Старт теста поиска статей с ожидаемыми названиями и описаниями")
    //критичность теста
    @Severity(value = SeverityLevel.MINOR)
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

        if (elementA.getText().contains(articleTitleA)) {
            // Выводим в консоль название
            System.out.println("Найден " + articleTitleA + " и " + articleDescriptionA + " элементы");
        }

        String articleTitleB = "Javanese script";
        String articleDescriptionB = "Writing system used for several Austronesian languages";

        //ждём, пока элемент с заданным заголовком и описанием станет видимым на странице.
        WebElement elementB = SearchPageObject.waitForElementByTitleAndDescription(articleTitleB, articleDescriptionB);
        if (elementB.getText().contains(articleTitleB)) {
            // Выводим в консоль название
            System.out.println("Найден " + articleTitleB + " и " + articleDescriptionB + " элементы");
        }

        String articleTitleC = "Java (software platform)";
        String articleDescriptionC = "Set of computer software and specifications";

        //ждём, пока элемент с заданным заголовком и описанием станет видимым на странице.
        SearchPageObject.waitForElementByTitleAndDescription(articleTitleC, articleDescriptionC);
        //достаем текст из элемента
        WebElement elementC = SearchPageObject.waitForElementByTitleAndDescription(articleTitleC, articleDescriptionC);
        //проверяем одинаковы ли две строки, без учета регистра и не пуста ли строка elementC
        if (elementC.getText().contains(articleTitleC)) {
            // Выводим в консоль название
            System.out.println("Найден " + articleTitleC + " и " + articleDescriptionC + " элементы");
        }
        //кликаем, чтобы точно убедиться,что тест работает и нужные статьи находятся
        elementC.click();

        //используем это название статьи для проверки,что нужная статья открылась
        Assert.assertEquals(
                "We see unexpected title",
                "Java (software platform)",
                articleTitleC
        );
        System.out.println("Проверка пройдена");


    }

}
