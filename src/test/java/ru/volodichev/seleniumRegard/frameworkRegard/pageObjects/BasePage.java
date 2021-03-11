package ru.volodichev.seleniumRegard.frameworkRegard.pageObjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.volodichev.seleniumRegard.frameworkRegard.WebDriverWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * todo Document type BasePage
 */
public abstract class BasePage {

    static Logger log = LogManager.getRootLogger();

    public static List<String> listProductInBasket = new ArrayList<String>();

    public WebDriverWrapper driver = WebDriverWrapper.getInstance();
    protected String pageName;
    protected String address;

    protected BasePage(String pageName, String address) {
        this.pageName = pageName;
        this.address = address;
    }

    /**
     * Закрывает текущую страницу
     */
    public void close() {
        log.info("Закрываю текущую страницу.");
        driver.close();
    }

    public void open() {
        log.info(String.format("Открываю страницу '%s' по адресу '%s'",
            this.pageName,
            this.address));
        driver.get(this.address);
    }

    public void goToCategory(String category, String subCategory) {
        log.info("Перехожу в категорию " + category + " подраздел " + subCategory);

        WebElement selectCategory = driver.findElement(By.xpath(String.format(
            "//*[@id='lmenu']//li[a[text()='%s']]", category)));
        selectCategory.click();

        WebElement selectSubcategory = driver.findElement(By.xpath(String.format(
            "//*[@id='lmenu']//li[a[text()='%s']]//li/a[text()='%s']", category, subCategory)));
        selectSubcategory.click();

        selectCategory = driver.findElement(By.xpath(String.format(
            "//*[@id='lmenu']//li[a[text()='%s']]", category)));
        selectCategory.click();
    }

    public void goToCategory(String category) {
        log.info("Перехожу в категорию " + category);

        WebElement selectCategory = driver.findElement(By.xpath(String.format(
            "//*[@id='lmenu']//li[a[text()='%s']]", category)));

        selectCategory.click();
        selectCategory = driver.findElement(By.xpath(String.format(
            "//*[@id='lmenu']//li[a[text()='%s']]//li/a[text()='%s']", category, "Все товары раздела")));
        selectCategory.click();
    }

    /**
     * Дефолтная реализация проверки, что конкретной странице.
     * По сути мы проверяем, что страничный объект, которым мы пользуемся, все еще актуален
     * и совпадает с тем, что мы видим в браузере.
     */
    public void checkPageURLCorrect() {
        Assertions.assertTrue(driver.getCurrentUrl().contains(address));
    }
}
