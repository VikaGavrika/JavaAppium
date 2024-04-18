package lib.UI;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;

public class MainPageObject {
    //инициализируем драйвер
    protected AppiumDriver driver;
    //пишем конструктор класса, к которому будут обращаться все наши тесты
    public MainPageObject(AppiumDriver driver){
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
        } else {
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
    protected void swipeUp(int timeOfSwipe) {
        // Определяем размер экрана
        Dimension size = driver.manage().window().getSize();
        // Вычисляем координаты
        int startY = (int) (size.height * 0.70); // Начальная точка Y (70% от высоты экрана)
        int endY = (int) (size.height * 0.30); // Конечная точка Y (30% от высоты экрана)
        int centerX = size.width / 2; // Центральная точка X
        //для отслеживания выполнения свайпа добавила исключения и логирование
        try {
            TouchAction touchAction = new TouchAction(driver);
            System.out.println("Начинаю свайп...");
            PointOption pointOption = PointOption.point(centerX, startY);
            touchAction.press(pointOption).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(timeOfSwipe))).moveTo(PointOption.point(centerX, endY)).release().perform();
            System.out.println("Свайп выполнен успешно!");
        } catch (Exception e) {
            // Обработка исключения
            System.out.println("Ошибка при выполнении свайпа: " + e.getMessage());
        }
    }
    //РАБОТАЕТ метод свайпа снизу-вверх
    public void verticalSwipe(int timeOfSwipe) {
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
    }
    //Быстрый свайп
    public void swipeQuick()
    {
        verticalSwipe(2000);

    }

    //метод, в котором будем свайпить до определенного элемента
    public void verticalSwipeToFindElement(String locator, String error_message, int max_swipes)
    {
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
    }
    public void swipeUPTitleElementAppear(String locator, String error_message, int max_swipes){
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
    }

    //метод, который выясняет есть ли элемент на странице
    // для IOS так как в айос элемент всегда на странице, даже если его не видно. А нам надо найти элемент в конкретном месте
    public boolean isElementLocatedOnTheScreen(String locator){
        //выяснять есть ли элемент будем по его положению по вертикальной оси у по отношению ко всей длине страницы
        //находим елемент по локатору и получаем его расположение по оси Y
        int  element_location_by_y = this.waitForElementPresent(locator, "Cannot find element by locator", 15).getLocation().getY();
        //находим длину всего экрана
        int screen_size_by_y = driver.manage().window().getSize().getHeight();
        //если переменная будет больше чем размер экрана по высоте, то будет фолс, переменной нет на зкране
        //как только доскролим то переменной, то получаем тру
        return element_location_by_y < screen_size_by_y;

    }



    //метод перемещения элемента по координатам
    public void moveButton(int timeOfSwipe, int startX, int startY, int endX,int endY) {

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
    }
    //Метод свайпа влево в Андройд
    public void leftSwipe(int timeOfSwipe, int startX, int startY, int endX,int endY) {
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
    }
    //Метод свайпа влево в Айос
    public void leftSwipeWithOffsetX(int timeOfSwipe, int elementX, int offsetX,  int startY,int endY) {
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
    }

}
