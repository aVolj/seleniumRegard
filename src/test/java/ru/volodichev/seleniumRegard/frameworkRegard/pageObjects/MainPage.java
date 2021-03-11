package ru.volodichev.seleniumRegard.frameworkRegard.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.Locatable;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.volodichev.seleniumRegard.frameworkRegard.WebDriverWrapper;

/**
 * todo Document type MainPage
 */
public class MainPage extends BasePage {
    Actions actions;

    public MainPage() {
        super("Главная страница", "https://www.regard.ru/");
        actions = new Actions(driver.getDriver());
    }

    public void search(String searchQuery){
        setSearchQuery(searchQuery);
        clickSearchButton();
    }

    private void clickSearchButton() {
            log.info("Кликаю по кнопке 'Найти'.");
            WebElement searchButton = driver.findElement(By.xpath("//button[text()='Найти']"));
            searchButton.click();

    }

    public void setSearchQuery(String searchQuery){
        log.info("Ввожу запрос в строку поиска.");
        WebElement searchField = driver.findElement(By.xpath("//input[@name='query']"));
        searchField.sendKeys(searchQuery);
    }
}
