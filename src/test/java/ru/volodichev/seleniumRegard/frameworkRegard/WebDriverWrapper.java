package ru.volodichev.seleniumRegard.frameworkRegard;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

/**
 * todo Document type WebDriverWrapper
 */
public class WebDriverWrapper {
    private static Logger log = LogManager.getRootLogger();

    private ChromeDriver driver = null;
    private WebDriverWait wait = null;
    boolean colorElements = false;

    private static WebDriverWrapper instance = null;

    private final String scrollElementIntoMiddle = "var viewPortHeight = Math.max(document.documentElement.clientHeight, window.innerHeight || 0);"
        + "var elementTop = arguments[0].getBoundingClientRect().top;"
        + "window.scrollBy(0, elementTop-(viewPortHeight/2));";

    /**
     * WebDriverWrapper построен в соответствии с паттерном Singleton (Одиночка).
     * Всегда на все классы будет доступен единственный экземпляр WebDriverWrapper.
     * Экземпляр хранится в поле instance, конструктор специально объявлен приватным.
     * @return
     */
    public static WebDriverWrapper getInstance() {
        if (instance == null) {
            instance = new WebDriverWrapper();
            log.debug("Создал экземпляр обертки WebDriver (синглтон).");
        }
        return instance;
    }

    private WebDriverWrapper(){
        log.debug("Инициализирую экземпляр обертки WebDriver.");

        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        System.setProperty("webdriver.chrome.driver", "src/test/resources/bin/chromedriver.exe");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();

        colorElements = true;

        wait = new WebDriverWait(driver, 5, 200);
    }

    public WebDriverWrapper get(String url) {
        log.debug("Перехожу по адресу [" + url + "]");
        driver.navigate().to(url);
        return this;
    }

    public String getCurrentUrl(){
        log.debug("Определяю текущий URL страницы..");
        return driver.getCurrentUrl();
    }

    public WebElement findElement(By locator) throws TimeoutException {
        WebElement element = null;
        int maxCount = 3;
        for (int count = 0; count < maxCount; count++) {
            log.debug(String.format("Пробую найти элемент [%s] - попытка #%d", locator.toString(), count));

            try {
                element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
                element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
                element = wait.until(ExpectedConditions.elementToBeClickable(locator));

                ((JavascriptExecutor) driver).executeScript(scrollElementIntoMiddle, element);


                //Подсветка на странице найденного нами элемента
                if (colorElements) {
                    driver.executeScript("arguments[0]['style']['backgroundColor']='darksalmon';", element);
                }

                log.debug("Элемент найден.");
                return element;
            } catch (StaleElementReferenceException e) {
                log.debug("DOM-дерево изменилось. Искомый объект устарел.");
                //продолжаем попытки поиска
            } catch (WebDriverException e) {
                log.debug("Элемент не был найден.");
            }
        }
        log.error("Элемент не был найден.");
        throw new NoSuchElementException("Элемент не был найден.");
    }

    public List<WebElement> findElements(By locator) throws TimeoutException {
        return driver.findElements(locator);
    }

    public WebDriverWrapper close(){
        String tabTitle = driver.getTitle();
        log.debug(String.format("Закрываю текущую вкладку [%s]", tabTitle));
        driver.close();
        return this;
    }

    public void quit(){
        log.debug("Закрываю все вкладки и окна, завершаю процесс webdriver.");
        driver.quit();
    }

    public WebDriverWait getWait() {
        return wait;
    }

    public WebDriver getDriver(){
        return driver;
    }
}
