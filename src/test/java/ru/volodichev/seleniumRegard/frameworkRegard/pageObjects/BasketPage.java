package ru.volodichev.seleniumRegard.frameworkRegard.pageObjects;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

/**
 * todo Document type BasketPage
 */
public class BasketPage extends BasePage{

    public BasketPage() {
        super("Корзина", "https://www.regard.ru/basket/");
    }

    public List<String> getProductInBasket(){
        List<WebElement> webElementList;
        List<String> listNameProduct = new ArrayList<>();

        try {

            webElementList = driver.findElements(By.xpath("//tr[@class='goods_line']"));

            for (WebElement webElement: webElementList){
                listNameProduct.add(webElement.findElement(By.xpath("./td[@class='t2']/a")).getText());
            }
        }catch (StaleElementReferenceException exception){
            return null;
        }
        return listNameProduct;
    }

    public void clearBasket() {
        log.info("Нажимаем кнопку очистить корзину");
        driver.findElement(By.xpath("//a[@title='Очистить корзину']")).click();
    }

    public void alertConfirm() {
        driver.getDriver().switchTo().alert().accept();
    }

    public void basketInEmpty() {
        driver.findElement(By.xpath("//h3[@class='alarm']"));
    }
}
