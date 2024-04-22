package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.UI.ArticlePageObject;
import lib.UI.SearchPageObject;
import lib.UI.factories.ArticlePageObjectFactory;
import lib.UI.factories.SearchPageObjectFactory;
import org.junit.Test;

public class ChangeAppConditionTests extends CoreTestCase {
    //Тесты на ориентации и бэкграунт

    //Тест11, который вводит значение в поиск, выбирает статью, после поворачивать экран телефона,
    // проверять, что название статьи не изменилось. Только айос и Андройд
    @Test
    public void testChangeScreenOrientationOnSearchResults()
    {
        //Если запуск в браузере, то просто возвращаем, этот тест будет пропускаться.
        if (Platform.getInstance().isMW()){
            return;
        }
        //пропустить онбординг
        this.skipOnboarding();

        //инициализация
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        //поиска строки элемента и клика
        SearchPageObject.initSearchInputAndClick();
        //поиск элемента и отправки значения в поле
        SearchPageObject.typeSearchLine("Java");
        //Поиск элемента и клик по нему
        SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        //Работа с заголовком статьи. Инициализация
        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);;
        //получаем название заголовок статьи до ротации, чтобы в дальнейшем ее сравнить после ротации экрана
        //аттрибут (текст), который будем получать, запишем в переменную
        String title_before_rotation = ArticlePageObject.getArticleTitle("Object-oriented programming language");

        //повернуть телефон,в скобках указываем в какую сторону хотим повернуть альбомная- горизонт или портретная- вертик
        this.rotateScreenLandscape();

        //снова получаем значение названия статьи
        String title_after_rotation = ArticlePageObject.getArticleTitle("Object-oriented programming language");

        //Сравниваем два значения
        assertEquals(
                "Article title have been changed after rotation",
                title_before_rotation,
                title_after_rotation
        );
        //сделаем еще одну ротацию
        this.rotateScreenPortrait();

        //снова получаем значение названия статьи после ротации
        String title_after_second_rotation = ArticlePageObject.getArticleTitle("Object-oriented programming language");


        // сравниваем два значения
        assertEquals(
                "Article title have been changed after rotation",
                title_before_rotation,
                title_after_second_rotation
        );
        //еще раз перевернем, чтобы проверить, вернет ли метод экран в портретную ориентацию
        this.rotateScreenLandscape();
        //переворот экрана в вертикальную ориентацию, кроме случаев, когда уже в вертикальной ориентации
        this.resetScreenOrientation();

    }

    //Тест12, который вводит значение в поиск, находить определенный элемент в результатах поиска,
    // потом приложение сворачиваем, через некоторое время разворачиваем, после открытия приложения проверяем,
    // что элемент, остался на месте.
    @Test
    public void testCheckSearchArticleInBackground()
    {
        //Если запуск в браузере, то просто возвращаем, этот тест будет пропускаться.
        if (Platform.getInstance().isMW()){
            return;
        }

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

        //отправляем приложение в бэкграунд
        this.backgroundApp(5);

        //после этого приложение автоматически развернется

        //убеждаемся,что та же самая статья действительно присутствует
        SearchPageObject.waitForSearchResult("Object-oriented programming language");

    }
}
