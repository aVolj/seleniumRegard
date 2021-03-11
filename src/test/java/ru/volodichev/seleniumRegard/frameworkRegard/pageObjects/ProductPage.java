package ru.volodichev.seleniumRegard.frameworkRegard.pageObjects;

import org.openqa.selenium.By;

/**
 * todo Document type ProductPage
 */
public class ProductPage extends BasePage{
    public ProductPage(String address) {
        super("Страница товара", address);
    }

    public void setAddress(String address){
        this.address = address;
    }

    public void addProductInBasket() {
        log.info("Добавляю товар в корзину со страницы товара");
        driver.findElement(By.xpath("//a[@id='add_cart']")).click();
        listProductInBasket.add(driver.findElement(By.xpath("//div[@class='goods_header']/h1")).getText());

    }

    public void goToBasket() {
        log.info("Перехожу в корзину после добавления товара");
        driver.findElement(By.xpath("//a[@id='add_cart']")).click();
    }
}
