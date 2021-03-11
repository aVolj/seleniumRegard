package ru.volodichev.seleniumRegard.frameworkRegard.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * todo Document type CategoryPage
 */
public class CatalogPage extends BasePage{

    public CatalogPage(String address) {
        super("Страница категории товара", address);
    }

    public void setAddress(String address){
        this.address = address;
    }

    public String getAddress(){
        return this.address;
    }

    public void selectAndPutProduct(int number) {
        log.info("Добавляем товар в корзину из списка");
        WebElement element = driver.findElement(By.xpath("(//div[@class='bcontent'])[1]"));

        listProductInBasket.add(element.findElement(By.xpath("./div[@class='aheader']/a")).getText());
        element.findElement(By.xpath(".//a[@class='cart']")).click();
    }

    public void openPageProduct(int number) {
        log.info("Открываем страницу товара");
        driver.findElement(By.xpath("(//div[@class='bcontent']/div[@class='aheader'])["+number+"]")).click();
    }

    public void openBasket(){
        log.info("Переходим в корзину");
        driver.findElement(By.xpath("//div[@id='basket']")).click();
    }

    public boolean checkProductSearch() {
        log.info("Смотрим, что нашелся хотя бы один товар");
        try{
        if(driver.findElements(By.xpath("//div[@class='bcontent']")).size() > 1){
            return true;
        }else {
            return false;
        }
        }catch (StaleElementReferenceException exception){
            return false;
        }
    }

    public boolean checkNameProduct(int n, String s) {
        WebElement element = driver.findElement(By.xpath("(//div[@class='bcontent']/div[@class='aheader']/a)["+n+"]"));
        if(element.getText().toLowerCase().contains(s.toLowerCase())) {
            return true;
        }else {
            return false;
        }
    }
}
