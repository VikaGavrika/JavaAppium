package lib.UI;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import lib.Platform;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;

public class MainPageObject {
    //инициализируем драйвер
    protected RemoteWebDriver driver;
    //пишем конструктор класса, к которому будут обращаться все наши тесты
    public MainPageObject(RemoteWebDriver driver){
        this.driver = driver;
    }
    //метод для определения типа локатора

    //метод для определения локатор elementId


    //private By getLocatorByString(String locator_with_type){
        //записываем в переменную значение строки "тип локатора", который передаем в этот метод, и делит из по символу ":"
        //String[] exploded_locator = locator_with_type.split(Pattern.quote(":"),2);
        //String by_type = exploded_locator[0];
        //String locator = exploded_locator[1];
        //логика для разделения локаторов
        //if(by_type.equals("xpath")){
            //return By.xpath(locator);
        //} else if (by_type.equals("id")){
            //return By.id(locator);
        //} else {
            //throw new IllegalArgumentException("Cannot get type of locator. locator: " +locator_with_type);
        //}
    //}

    //Для Комбинированных локаторов
    private By getLocatorByString(String locator_with_type) {
        //проверяем начинается ли строка с "xpath:"
        if (locator_with_type.startsWith("xpath:")) {
            //удаляет префикс "xpath:" и строка будет начинаться с 6го символа
            return By.xpath(locator_with_type.substring(6)); // Remove "xpath:" prefix
        } else if (locator_with_type.startsWith("id:")) {
            //удаляет префикс "id:" и строка будет начинаться с 3го символа
            return By.id(locator_with_type.substring(3)); // Remove "id:" prefix

        } else if (locator_with_type.startsWith("css:")) {
            //удаляет префикс "id:" и строка будет начинаться с 3го символа
            return By.cssSelector(locator_with_type.substring(4)); // Remove "css:" prefix
        }
        else {
            throw new IllegalArgumentException("Unsupported locator type: " + locator_with_type);
        }
    }



    //переносим все методы, которыми пользуются тесты

    public void assertElementPresent(String locator){
        By by = this.getLocatorByString(locator);
        WebDriverWait wait = new WebDriverWait(driver, 15);
        WebElement titleElement = wait.until(ExpectedConditions.presenceOfElementLocated(by));
        //проверка, что элемент присутствует, не null
        Assert.assertNotNull("Cannot find element", titleElement);
        // Проверка, что элемент отображается
        Assert.assertTrue("Element is not displayed", titleElement.isDisplayed());
        // вывод в консоль
        System.out.println("Element is Present");

    }




    //метод получения заголовка статьи
    public String waitForElementAndGetAttribute(String locator, String attribute, String error_message, long timeoutInSeconds ){
        WebElement element = waitForElementPresent(locator, error_message,timeoutInSeconds);
        return element.getAttribute(attribute);

    }

    //метод считает количество элементов, которые нашли
    public int getAmountOfElements(String locator)
    {
        By by = this.getLocatorByString(locator);
        //Функция, которая создает список
        List elements = driver.findElements(by);
        //возвращаем кол-во элементов,которые были найдены
        return elements.size();
    }
    //метод, который будет определять есть ли элемент на странице или нет. булевое значение есть элемент или нет
    public boolean isElementPresent(String locator){
        return getAmountOfElements(locator) > 0;
    }

    //клик по элементу, когда есть анимация
    //метод,который будет пытаться кликать снова и снова, пока не пропадет сообщение об ошибке
    public void tryClickElementWithFewAttempts(String locator, String error_message,int amount_of_attempts){
        //сколько раз кликнули на момент запуска.счетчик кликов
        int current_attempts = 0;
        //останавливать клики. если кликнули больше ра чем нужно
        boolean need_more_attempts = true;
        //цикл с блоком для обработки ошибок
        System.out.println("Начинаю клики по элементу");
        while (need_more_attempts) {
            try {
                //если кликнем по элементу и не получится кликнуть
                this.waitForElementAndClick(locator,error_message,3);
                //то до этой строки код не дойдет и выпадет в ошибку
                need_more_attempts = false;
                //если удачно кликнули по элементу,то код дойдет до строки,выпадет фолс и цикл прекратится
            }catch (Exception e) {
                //если кликов станет больше макс значения, то делаем еще раз клик и потом ошибка
                if (current_attempts > amount_of_attempts) {
                    this.waitForElementAndClick(locator,error_message,5);
                }
            }
            //счетчик увелич на 1 после каждого цикла
            ++current_attempts;
        }

    }


    //метод, который находит элемент
    public WebElement findElement(String locator) {
        By by = this.getLocatorByString(locator);
        // Находим элемент
        WebElement element = driver.findElement(by);
        return element;

    }

    //метод, проверяющий, что не нашлось ни одного элемента с текстом из поиска
    public void assertNoElementsPresentWithText(String locator, String search_line, String error_message){
        //получаем кол-во элементов
        int amount_of_elements = getAmountOfElements(locator);
        // Вывод в консоль сколько всего результатов статей
        System.out.println("Найден " + amount_of_elements + " элемент с текстом.");
        //если нашли элемент, то проверяем, какой текст он содержит. Если содержит текст поиска,
        // то выдаем исключение с сообщением
        if (amount_of_elements>0) {
            //получаем список найденных элементов
            By by = this.getLocatorByString(locator);
            List <WebElement> elements = driver.findElements(by);
            // Проверить каждый элемент
            for (WebElement element : elements) {
                String elementText = element.getText();
                // Если элемент содержит ожидаемый текст, вывести сообщение об ошибке
                if (elementText.contains(search_line)) {
                    //передаем элемент by в строковое значение, предполагается, что этот элемент отсутствует
                    //формируем строку с этим элементом
                    String default_message = "An element '" + locator + "'supposed to be not present";
                    //обозначает проблему, что этого элемента не должно быть, а он есть, и кидаем сообщение об этом
                    throw new AssertionError(default_message + " " + error_message);
                }
            }
            // Если ни один элемент не содержит текст с поиска, то успех
            System.out.println("No elements found with text: " + search_line);
        }

    }



    //метод, который проверяет наличие нескольких результатов поиска на странице с ожидаемым текстом
    public void assertMultipleSearchResultsWithText(String locator, WebElement resultsList, String expectedText) {
        // Получаем кол-во статей с ожидаемым словом
        By by = this.getLocatorByString(locator);
        int resultsCount = resultsList.findElements(by).size();
        //считаем сколько заголовков содержит ожидаемый текст
        //инициализируем переменную, присваиваем значение 0 для инициализации счетчика
        int expectedTextResultsCount = 0;
        //перебираем в цикле все элементы в листе
        for (WebElement result : resultsList.findElements(by)) {
            //получаем текстовое содержимое текущего WebElement (представляющего результат поиска) и преобразуем его
            // в нижний регистр, для более корректного сравнения в будущем, также проверяем, содержит ли expectedText.
            if (result.getText().toLowerCase().contains(expectedText)) {
                String resultText = result.getText().toLowerCase();
                // Проверка, что ожидаемое слово есть в каждом результате
                String errorMessage = "Ожидалось, что слово '" + expectedText + "' будет найдено в " + result.getText() + ", но его там нет.";
                assert resultText.contains(expectedText.toLowerCase()) : errorMessage;
                //Если ожидаемое слово найдено в текущем результате поиска, значение expectedWordResultsCount увеличивается на 1.
                // отслеживаем общее количество результатов поиска, содержащих ожидаемое слово
                expectedTextResultsCount++;
            }
        }

        // проверяем, что больше, чем один результат, содержит ожидаемое слово
        String errorMessage = "Ожидалось несколько результатов с ожидаемым текстом, но найдено только .. " + expectedTextResultsCount;
        assert expectedTextResultsCount > 1 : errorMessage;

        // Вывод в консоль сколько всего результатов статей
        System.out.println("Найдено " + resultsCount + " статей.");

        // Вывод в консоль сколько результатов содержит ожидаемое слово
        System.out.println("Найдено " + expectedTextResultsCount + " статей с текстом '" + expectedText + "'.");


    }


    //метод, который проверяет наличие ожидаемого текста у элемента.
    public void assertElementHasText(WebElement element, String expectedText) {
        String actualText = element.getText();
        assertEquals(
                "Element does not contain expected text",
                expectedText,
                actualText
        );
    }


    //метод, котрый будет искать элемент по любому атрибуту
    public WebElement waitForElementPresent(String locator, String error_message, long timeoutInSecond) {
        By by = this.getLocatorByString(locator);
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSecond);
        wait.withMessage(error_message + "\n");
        return wait.until(
                //ждем выполнения конкретного условия, ждем элемент by
                ExpectedConditions.presenceOfElementLocated(by)

        );

    }


    //метод адаптированный, который ищет элемент с дефолтной задержкой в 15 сек
    public WebElement waitForElementPresent(String locator, String error_message) {
        return waitForElementPresent(locator, error_message, 15);
    }

    //метод, испол-я который тесты сначала будут дожидаться элемента, а после этого происзойдет клик
    public WebElement waitForElementAndClick(String locator, String error_message, long timeoutInSecond) {
        WebElement element = waitForElementPresent(locator, error_message, timeoutInSecond);
        element.click();
        return element;
    }

    //метод, испол-я который тесты сначала будут дожидаться элемента, а после этого происзойдет отправка текста
    public WebElement waitForElementAndSendKeys(String locator, String value, String error_message, long timeoutInSecond) {
        WebElement element = waitForElementPresent(locator, error_message, timeoutInSecond);
        element.sendKeys(value);
        return element;
    }

    //Метод отсутствия элемента на странице
    public boolean waitForElementNotPresent(String locator, String error_message, long timeoutInSecond) {
        By by = this.getLocatorByString(locator);
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSecond);
        wait.withMessage(error_message + "\n");
        return wait.until(
                //ждем выполнения конкретного условия, ждем элемент by
                ExpectedConditions.invisibilityOfElementLocated(by)
        );

    }

    //метод очистки поля ввода
    public WebElement waitForElementAndClear(String locator, String error_message, long timeoutInSecond) {
        WebElement element = waitForElementPresent(locator, error_message, timeoutInSecond);
        element.clear();
        return element;

    }

    //НЕ РАБОТАЕТ метод свайпа снизу-вверх
    protected void swipeUp(int timeOfSwipe)
    {
        //проверяем, что драйвер является случаем Аппиум драйвера (то есть тест работает на Андройд или айос),
        //то выполняем драйвер perform
        if (driver instanceof AppiumDriver){
            // Определяем размер экрана
            Dimension size = driver.manage().window().getSize();
            // Вычисляем координаты
            int startY = (int) (size.height * 0.70); // Начальная точка Y (70% от высоты экрана)
            int endY = (int) (size.height * 0.30); // Конечная точка Y (30% от высоты экрана)
            int centerX = size.width / 2; // Центральная точка X
            //для отслеживания выполнения свайпа добавила исключения и логирование
            try {
                TouchAction touchAction = new TouchAction((AppiumDriver)driver);
                System.out.println("Начинаю свайп...");
                PointOption pointOption = PointOption.point(centerX, startY);
                touchAction.press(pointOption).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(timeOfSwipe))).moveTo(PointOption.point(centerX, endY)).release().perform();
                System.out.println("Свайп выполнен успешно!");
            } catch (Exception e) {
                // Обработка исключения
                System.out.println("Ошибка при выполнении свайпа: " + e.getMessage());
            }
        }else {
            //если это не так, то есть запустили тест в браузере,то ничего не делаем,
            // кроме напоминания, что данный метод для браузера не работает
            System.out.println("Method rotateScreenPortrait() does nothing for platform " +Platform.getInstance().getPlatformVar());
        }
    }


    //РАБОТАЕТ метод свайпа снизу-вверх. Только для андройд и айос. Для браузера не подходит
    public void verticalSwipe(int timeOfSwipe)
    { //проверяем, что драйвер является случаем Аппиум драйвера (то есть тест работает на Андройд или айос),
        //то выполняем драйвер perform
        if (driver instanceof AppiumDriver){
            Dimension size = driver.manage().window().getSize();
            int startY = (int) (size.height * 0.70);
            int endY = (int) (size.height * 0.20);
            int centerX = size.width / 2;

            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH,"finger");
            Sequence swipe = new Sequence(finger,1);

            System.out.println("Начинаю свайп...");

            //Двигаем палец на начальную позицию
            swipe.addAction(finger.createPointerMove(Duration.ofMillis(timeOfSwipe),
                    PointerInput.Origin.viewport(),centerX, startY));
            //Палец прикасается к экрану
            swipe.addAction(finger.createPointerDown(0));

            //Палец двигается к конечной точке
            swipe.addAction(finger.createPointerMove(Duration.ofMillis(timeOfSwipe),
                    PointerInput.Origin.viewport(),centerX, endY));

            //Убираем палец с экрана
            swipe.addAction(finger.createPointerUp(0));

            //Выполняем действия
            driver.perform(Arrays.asList(swipe));
            System.out.println("Свайп выполнен успешно!");

        } else {
            //если это не так, то есть запустили тест в браузере,то ничего не делаем,
            // кроме напоминания, что данный метод для браузера не работает
            System.out.println("Method rotateScreenPortrait() does nothing for platform " +Platform.getInstance().getPlatformVar());
        }

    }
    //Быстрый свайп Только для андройд и айос. Для браузера не подходит
    public void swipeQuick()
    {
        if (driver instanceof AppiumDriver){
            verticalSwipe(2000);
        }else {
            //если это не так, то есть запустили тест в браузере,то ничего не делаем,
            // кроме напоминания, что данный метод для браузера не работает
            System.out.println("Method rotateScreenPortrait() does nothing for platform " +Platform.getInstance().getPlatformVar());
        }

    }

    //метод, в котором будем свайпить до определенного элемента Только для андройд и айос. Для браузера не подходит
    public void verticalSwipeToFindElement(String locator, String error_message, int max_swipes)
    {
        if (driver instanceof AppiumDriver){
            By by = this.getLocatorByString(locator);
            //поиск всех элементов и считаем кол-во найденных элементов
            //цикл будет работать (свайпить) пока функция не находит ни одного элемента, как только элемент найдется, цикл завершится
            //если превысим кол-во свайпов, то цикл остановится
            int already_swiped = 0;      //начальный счетчик свайпов
            while(driver.findElements(by).size() == 0)
            {
                //остановка цикла, если свайпы превысили макс значение
                if (already_swiped > max_swipes){
                    //проверяем, что этого элемента все еще нет
                    waitForElementPresent(locator, "Cannot find element by swiping up. \n" +error_message,15);
                    //если элемент нашелся, выходим с метода и идем дальше по коду
                    return;
                }
                try {
                    swipeQuick();
                }catch (Exception e) {
                    // Обработка исключения
                    System.out.println("Ошибка при выполнении свайпа: " + e.getMessage());
                }
                ++already_swiped;    //счетчик свайпов с каждым циклом
                // Вывод в консоль сколько свайпов было сделано
                System.out.println("сделано " + already_swiped + " свайпов");
            }

        }else {
            //если это не так, то есть запустили тест в браузере,то ничего не делаем,
            // кроме напоминания, что данный метод для браузера не работает
            System.out.println("Method rotateScreenPortrait() does nothing for platform " +Platform.getInstance().getPlatformVar());
        }

    }

    //Только для андройд и айос. Для браузера не подходит
    public void swipeUPTitleElementAppear(String locator, String error_message, int max_swipes)
    {
        if (driver instanceof AppiumDriver){
            int already_swiped = 0;
            //пока элемент не находится на экране
            while (!this.isElementLocatedOnTheScreen(locator)){
                //остановка цикла, если свайпы превысили макс значение
                if (already_swiped > max_swipes){
                    //проверяем, что этого элемента все еще нет
                    Assert.assertTrue(error_message, this.isElementLocatedOnTheScreen(locator));
                }
                try {
                    //пока элемент не будет найден на экране, продолжаем свайпить
                    swipeQuick();
                }catch (Exception e) {
                    // Обработка исключения
                    System.out.println("Ошибка при выполнении свайпа: " + e.getMessage());
                }
                //добавляем значение, пока кол-во свайпов не станет больше указ кол-ва. потом прекращаем цикл
                ++already_swiped;    //счетчик свайпов с каждым циклом
                // Вывод в консоль сколько свайпов было сделано
                System.out.println("сделано " + already_swiped + " свайпов");
            }
        }else {
            //если это не так, то есть запустили тест в браузере,то ничего не делаем,
            // кроме напоминания, что данный метод для браузера не работает
            System.out.println("Method rotateScreenPortrait() does nothing for platform " +Platform.getInstance().getPlatformVar());
        }

    }

    //метод, который выясняет есть ли элемент на странице
    // для IOS так как в айос элемент всегда на странице, даже если его не видно. А нам надо найти элемент в конкретном месте
    public boolean isElementLocatedOnTheScreen(String locator){
        //выяснять есть ли элемент будем по его положению по вертикальной оси у по отношению ко всей длине страницы
        //находим елемент по локатору и получаем его расположение по оси Y
        //getLocation указанный ниже не используются для web, поэтому для web будет if
        int  element_location_by_y = this.waitForElementPresent(locator, "Cannot find element by locator", 15).getLocation().getY();
        //для web
        if (Platform.getInstance().isMW()){
            JavascriptExecutor JSExecutor = (JavascriptExecutor) driver;
            //получаем текущую позицию прокрутки (верт отступ) от верха страницыю результат хранится в js_element
            Object js_result = JSExecutor.executeScript("return window.pageYOffset");
            //вычитаем позицию прокрутки из У-координаты элемента, чтобы скоректировать его положение
            // относительно видимой области окна просмотра
            //Integer.parseInt() преобразует js_result (который является String) в int
            element_location_by_y -= Integer.parseInt(js_result.toString());
        }
        //находим длину всего экрана Эта строка получает высоту видимой области экрана
        int screen_size_by_y = driver.manage().window().getSize().getHeight();
        //Функция возвращает true, если Y-координата элемента меньше высоты экрана, что означает, что элемент находится в пределах окна просмотра.
        //Она возвращает false, если Y-координата элемента больше или равна высоте экрана, что означает, что элемент не виден на экране.
        return element_location_by_y < screen_size_by_y;

    }



    //метод перемещения элемента по координатам Только для андройд и айос. Для браузера не подходит
    public void moveButton(int timeOfSwipe, int startX, int startY, int endX,int endY)
    {
        if (driver instanceof AppiumDriver){
            // Нажатие и перемещение кнопки
            try {
                //создаем PointerInput
                PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH,"finger");
                //создаем последовательность действий
                Sequence move = new Sequence(finger,1);
                System.out.println("Начинаю перемещение кнопки...");
                move.addAction(finger.createPointerMove(Duration.ofMillis(timeOfSwipe),
                        PointerInput.Origin.viewport(),startX, startY));
                //Палец прикасается к экрану
                move.addAction(finger.createPointerDown(1));

                //Палец двигается к конечной точке
                move.addAction(finger.createPointerMove(Duration.ofMillis(timeOfSwipe),
                        PointerInput.Origin.viewport(),endX, endY));

                //Убираем палец с экрана
                move.addAction(finger.createPointerUp(1));

                //Выполняем действия
                driver.perform(Arrays.asList( move));

                System.out.println("Перемещение выполнено успешно!");
            } catch (Exception e) {
                // Обработка исключения
                System.out.println("Ошибка при перемещении кнопки: " + e.getMessage());

            }
        }else {
            //если это не так, то есть запустили тест в браузере,то ничего не делаем,
            // кроме напоминания, что данный метод для браузера не работает
            System.out.println("Method rotateScreenPortrait() does nothing for platform " +Platform.getInstance().getPlatformVar());
        }

    }


    //Метод свайпа влево в Андройд Только для андройд и айос. Для браузера не подходит
    public void leftSwipe(int timeOfSwipe, int startX, int startY, int endX,int endY)
    {
        if (driver instanceof AppiumDriver){
            try {
                //создаем PointerInput
                PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
                //создаем последовательность действий
                Sequence swipe = new Sequence(finger, 1);
                System.out.println("Начинаю свайп влево...");
                swipe.addAction(finger.createPointerMove(Duration.ofMillis(timeOfSwipe),
                        PointerInput.Origin.viewport(), startX, startY));
                //Палец прикасается к экрану
                swipe.addAction(finger.createPointerDown(0));

                //Палец двигается к конечной точке
                swipe.addAction(finger.createPointerMove(Duration.ofMillis(timeOfSwipe),
                        PointerInput.Origin.viewport(), endX, endY));

                //Убираем палец с экрана
                swipe.addAction(finger.createPointerUp(0));

                //Выполняем действия
                driver.perform(Arrays.asList(swipe));

                System.out.println("Свайп влево выполнен успешно!");
            } catch (Exception e) {
                // Обработка исключения
                System.out.println("Ошибка при свайпе влево: " + e.getMessage());

            }
        }else {
            //если это не так, то есть запустили тест в браузере,то ничего не делаем,
            // кроме напоминания, что данный метод для браузера не работает
            System.out.println("Method rotateScreenPortrait() does nothing for platform " +Platform.getInstance().getPlatformVar());
        }

    }
    //Метод свайпа влево в Айос Только для айос. Для браузера не подходит
    public void leftSwipeWithOffsetX(int timeOfSwipe, int elementX, int offsetX,  int startY,int endY)
    {
        if (driver instanceof AppiumDriver){
            try {
                //создаем PointerInput
                PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
                //создаем последовательность действий
                Sequence swipe = new Sequence(finger, 1);
                System.out.println("Начинаю свайп влево...");

                swipe.addAction(finger.createPointerMove(Duration.ofMillis(timeOfSwipe),
                        PointerInput.Origin.viewport(), elementX, startY));
                //Палец прикасается к экрану
                swipe.addAction(finger.createPointerDown(0));

                //Палец двигается к конечной точке
                swipe.addAction(finger.createPointerMove(Duration.ofMillis(timeOfSwipe),
                        PointerInput.Origin.viewport(), offsetX , endY));

                //Убираем палец с экрана
                swipe.addAction(finger.createPointerUp(0));

                //Выполняем действия
                driver.perform(Arrays.asList(swipe));

                System.out.println("Свайп влево выполнен успешно!");
            } catch (Exception e) {
                // Обработка исключения
                System.out.println("Ошибка при свайпе влево: " + e.getMessage());

            }
        }else {
            //если это не так, то есть запустили тест в браузере,то ничего не делаем,
            // кроме напоминания, что данный метод для браузера не работает
            System.out.println("Method rotateScreenPortrait() does nothing for platform " +Platform.getInstance().getPlatformVar());
        }

    }

    //метод скролла для моб версии в браузере только для mobile_web
    public void scrollWebPageUp(){
        if (Platform.getInstance().isMW()){
            JavascriptExecutor JSExecutor = (JavascriptExecutor) driver;
            //скролл с 0 позиции до 250 пикселей
            JSExecutor.executeScript("window.scrollBy(0,250)");
        }else {
            System.out.println("Method rotateScreenPortrait() does nothing for platform " +Platform.getInstance().getPlatformVar());
        }
    }
    //метод скролл пока элемент не станет видимым.только для mobile_web
    public void scrollWebPageTitleElementNotVisible(String locator, String error_message, int max_swiped){
        //счетчик выполенных скроллов
        int already_swiped =0;
        //получаем объект
        WebElement element = this.waitForElementPresent(locator, error_message);
        //цикл будет выполняться пока элемент НЕ(!) покажется на экране
        //Цикл while продолжает выполняться до тех пор, пока элемент не окажется на экране
        // или не будет достигнуто максимальное количество прокруток.
        while (!this.isElementLocatedOnTheScreen(locator)){
            scrollWebPageUp();
            //увелич значение переменной на 1
            ++already_swiped;
            //проверка после каждого скоролла,не превысил ли макс знач, если превыш макс переход к след строке
            if (already_swiped > max_swiped){
                //проверка наличия элемента. проверяет отображается ли элемент
                //Если элемент не отображается после достижения максимального количества прокруток,
                // то выбрасывается исключение
                Assert.assertTrue(error_message,element.isDisplayed());
            }
        }
    }


}
