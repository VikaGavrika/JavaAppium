package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.UI.*;
import lib.UI.factories.ArticlePageObjectFactory;
import lib.UI.factories.MyListPageObjectFactory;
import lib.UI.factories.NavigationUIFactory;
import lib.UI.factories.NavigationUIFactory;
import lib.UI.factories.SearchPageObjectFactory;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import java.util.List;

public class MyListTests extends CoreTestCase {
    //Тесты связанные с MyLists списком сохраненных статей
    //Тест8. Поиск определенной статьи, выбрать статью, нажать на кнопку с выпадающем списком, после открытия выбрать
    // и нажать на кнопку из списка, в батоншите создать новый список (нажав на кнопку), ввести название списка в поле,
    // нажать ОК, выйти из статьи, нажать на кнопку списки, перейти на экран со спискими, выбрать один их них, нажать,
    // убедиться что в списке присутствует выбранная статья, удалить статью,
    // убедиться, что она удалена, тест будет считаться законченным

    //задаем переменную с названием списка, тк будем исп-ть ее в нескольких местах
    private static final String name_of_folder = "articles";
    private static final String login = "VikaGavrika";
    private static final String password = "GavrikaVika";


    @Test
    public void testSavedFirstArticleToMyList() throws InterruptedException {
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

        //Работа с заголовком статьи. Инициализация
        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);;
        //поиск заголовка нужной статьи
        ArticlePageObject.waitForTitleElement("Java (programming language)");
        //делаем отдельную переменную для названия статьи
        String article_title = ArticlePageObject.getArticleTitle("Java (programming language)");

        // добавляем статью в список статей для разных платформ
        if(Platform.getInstance().isAndroid()){
            ArticlePageObject.addArticleToMyList(name_of_folder);
            //нажать кнопку назад 3 раза, чтобы вернуться на главную страницу
            //цикл, повторяем код, пока не будет выполнено определенное условие.
            int i = 0;
            while (i < 3) {
                ArticlePageObject.closeArticle();
                i++;
            }

        } else if (Platform.getInstance().isIOS()) {
            ArticlePageObject.addArticlesToMySaved();
            //закрыть статью
            ArticlePageObject.closeArticle();
            //вернуться на главную
            ArticlePageObject.comeBackToMain();


        } else {
            ArticlePageObject.addArticlesToMySaved();
            //инициализ драйвера авторизации
            AuthorizationPageObject Auth = new AuthorizationPageObject(driver);
            //авторизация
            //дожидаемся кнопки логин (после подписки на действие клика в избранное) и нажимаем на нее
            Auth.clickAuthButton();
            Auth.enterLoginData(login, password);
            Auth.submitForm();
            //ждем пока средиректит обратно на страницу статьи
            ArticlePageObject.waitForTitleElement("programming language");
            //проверяем, что мы все еще на той же странице
            assertEquals("we are not  on the same page after login",
                    article_title,
                    ArticlePageObject.getArticleTitle("programming language")
            );

            //нажать на кнопку избранное в статье
            ArticlePageObject.addArticlesToMySaved();

        }

        //инициализация навигация по приложению
        NavigationUI NavigationUI = NavigationUIFactory.get(driver);
        //раскрываем меню навигации только для мобайл веба
        NavigationUI.openNavigation();
        //нажать кнопку Избранное (сохраненные) в меню
        NavigationUI.clickMyLists();

        //инициализация объектов в списке My list
        MyListPageObject MyListPageObject = MyListPageObjectFactory.get(driver);
        //если это андройд или веб версия в браузере, то просто возвращаемся к след действиям
        if (Platform.getInstance().isAndroid()){
            return;
        }else if (Platform.getInstance().isIOS()){
            //для Айос. закрываем возникшее мод окно
            MyListPageObject.close_modal_window();
        } else {
            System.out.println("Method close_modal_window() does nothing for platform " + Platform.getInstance().getPlatformVar());
        }

        //Для Андройд. поиск списка статей по названию, название задано в переменную выше. клик на список статей
        if (Platform.getInstance().isAndroid()){
            MyListPageObject.openFolderByName(name_of_folder);
        } else{
        System.out.println("Method openFolderByName() does nothing for platform " + Platform.getInstance().getPlatformVar());
        }

        //удаление статьи свайпом влево для разных платформ
        //для андройд
        if (Platform.getInstance().isAndroid()){
            MyListPageObject.swipeByArticleToDelete(article_title);
        } else if (Platform.getInstance().isIOS()){
            //для Айос
            MyListPageObject.swipeByArticleToDeleteFromIOSList(article_title);
        } else {
            //для мобайл веб
            MyListPageObject.deleteArticleFromMWList(article_title);
        }

        //убеждаемся, что нужной статьи нет в списке
        MyListPageObject.waitForArticleToDisappearByTitle(article_title);

    }

    //Ex5. Tecт13, сохранить две статьи в список, одну статью удалить, убелиться, что вторая статья осталась,
    // зайти в нее и сравнить заголовки
    @Test
    public void testSavedTwoArticleToMyList() throws InterruptedException {
        //пропустить онбординг
        this.skipOnboarding();

        //инициализация
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        //1 статья
        //поиска строки элемента и клика
        SearchPageObject.initSearchInputAndClick();
        //поиск элемента и отправки значения в поле
        SearchPageObject.typeSearchLine("Java");
        //Клик по статье
        SearchPageObject.clickByArticleWithSubstring("programming language");

        //Работа с заголовком статьи. Инициализация
        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);;
        //поиск заголовка нужной статьи
        ArticlePageObject.waitForTitleElement("Java (programming language)");
        //делаем отдельную переменную для названия статьи
        String title_first_article = ArticlePageObject.getArticleTitle("Java (programming language)");

        // добавляем статью в список статей для разных платформ
        if(Platform.getInstance().isAndroid()){
            ArticlePageObject.addArticleToMyList(name_of_folder);
            //нажать кнопку назад 3 раза, чтобы вернуться на главную страницу
            //цикл, повторяем код, пока не будет выполнено определенное условие.
            int i = 0;
            while (i < 3) {
                ArticlePageObject.closeArticle();
                i++;
            }

        } else if (Platform.getInstance().isIOS()) {
            ArticlePageObject.addArticlesToMySaved();
            //закрыть статью
            ArticlePageObject.closeArticle();
            //вернуться на главную
            ArticlePageObject.comeBackToMain();


        } else {
            ArticlePageObject.addArticlesToMySaved();
            //инициализ драйвера авторизации
            AuthorizationPageObject Auth = new AuthorizationPageObject(driver);
            //авторизация
            //дожидаемся кнопки логин (после подписки на действие клика в избранное) и нажимаем на нее
            Auth.clickAuthButton();
            Auth.enterLoginData(login, password);
            Auth.submitForm();
            //ждем пока средиректит обратно на страницу статьи
            ArticlePageObject.waitForTitleElement("programming language");
            //проверяем, что мы все еще на той же странице
            assertEquals("we are not  on the same page after login",
                    title_first_article,
                    ArticlePageObject.getArticleTitle("programming language")
            );

            //нажать на кнопку избранное в статье
            ArticlePageObject.addArticlesToMySaved();

        }
        //2 статья
        //поиска строки элемента и клика
        SearchPageObject.initSearchInputAndClick();
        //поиск элемента и отправки значения в поле
        SearchPageObject.typeSearchLine("Appium");
        //Клик по статье
        SearchPageObject.clickByArticleWithSubstring("Automation for Apps");
        //поиск заголовка нужной статьи
        ArticlePageObject.waitForTitleSecondElement("Appium");
        //делаем отдельную переменную для названия статьи
        String title_second_article = ArticlePageObject.getArticleSecondTitle("Appium");

        // добавляем статью в список статей
        if(Platform.getInstance().isAndroid()){
            ArticlePageObject.addSecondArticleToMyList(name_of_folder);
        } else if (Platform.getInstance().isIOS()) {
            ArticlePageObject.addArticlesToMySaved();
            //закрыть статью
            ArticlePageObject.closeArticle();
            //вернуться на главную
            ArticlePageObject.comeBackToMain();


        } else {
            ArticlePageObject.addArticlesToMySaved();

        }

        //инициализация навигация по приложению
        NavigationUI NavigationUI = NavigationUIFactory.get(driver);
        //раскрываем меню навигации только для мобайл веба
        NavigationUI.openNavigation();
        //нажать кнопку Save в меню
        NavigationUI.clickMyLists();

        //инициализация объектов в списке My list
        MyListPageObject MyListPageObject = MyListPageObjectFactory.get(driver);
        //Только для Айос. закрываем мод окно. для остальных платформ метод не работает
        if (Platform.getInstance().isIOS()){
            MyListPageObject.close_modal_window();
        }else {
            System.out.println("Method close_modal_window() does nothing for platform " + Platform.getInstance().getPlatformVar());
        }


        //кол-во сохраненных статей до удаления
        int actualSavedArticlesCountBeforeDelete= MyListPageObject.getSavedArticleCount(20);


        //убеждаемся, что есть заголовок первой статьи в открывшемся списке
        MyListPageObject.waitForArticleToAppearByTitle(title_first_article);
        //Выводим в консоль название
        System.out.println("Найден " + title_first_article + " элемент с текстом.");
        //убеждаемся что есть вторая статья в открывшемся списке
        MyListPageObject.waitForArticleToAppearByTitle(title_second_article);
        //Выводим в консоль название
        System.out.println("Найден " + title_second_article + " элемент с текстом.");

        //удаление статьи свайпом влево для разных платформ
        if (Platform.getInstance().isAndroid()){
            MyListPageObject.swipeByArticleToDeleteFromList(title_second_article);
        } else if (Platform.getInstance().isIOS()){
            //для Айос
            MyListPageObject.swipeByArticleToDeleteFromIOSList(title_second_article);
        } else {
            //для мобайл веб
            MyListPageObject.deleteArticleFromMWList(title_second_article);
        }

        //убеждаемся, что нужной статьи нет в списке
        MyListPageObject.waitForArticleToDisappearByTitle(title_second_article);

        //кол-во сохраненных статей после удаления
        int actualSavedArticlesCountAfterDelete= MyListPageObject.getSavedArticleCount(20);


        //Первая проверка, сравнение длин двух списков сохраненных статей, список сохраненных статей уменьшен на 1 после удаления
        Assert.assertEquals(actualSavedArticlesCountBeforeDelete - 1, actualSavedArticlesCountAfterDelete);
        System.out.println("Проверка на сравнение количества статей пройдена");


        //Вторая проверка сравнения названия статей
        //убеждаемся, что нет второй статьи в списке по заголовку
        MyListPageObject.waitForArticleToDisappearByTitle(title_second_article);
        //убеждаемся что есть первая статья в списке и кликаем на нее
        MyListPageObject.waitForArticleToAppearByTitleAndClick(title_first_article);

        //Выводим в консоль название
        System.out.println("Найден " + title_first_article + " элемент с текстом после изменений в списке.");

        //снова получаем значение названия статьи
        String title_first_article_after_list_change = ArticlePageObject.getArticleTitle("Java (programming language)");

        //Сравниваем два значения и yбеждаемся, что заголовок в первой статье совпадает
        assertEquals(
                "Article title have been changed after rotation",
                title_first_article,
                title_first_article_after_list_change
        );
        System.out.println("Проверка на сравнение заголовков пройдена");

    }

}
