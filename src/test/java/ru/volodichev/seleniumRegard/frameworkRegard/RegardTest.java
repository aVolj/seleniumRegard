package ru.volodichev.seleniumRegard.frameworkRegard;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.volodichev.seleniumRegard.frameworkRegard.pageObjects.*;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

/**
 * todo Document type RegardTest
 */
public class RegardTest {
    MainPage mainPage = new MainPage();
    CatalogPage catalogPage = new CatalogPage(null);
    ProductPage productPage = new ProductPage(null);
    BasketPage basketPage = new BasketPage();
    /**
     * 1. Открыть главную страницу
     * 2. В левом боковом меню выбрать категорию материнские платы - в подменю выбрать Intel socket 1200
     * 3. Найти 5-ю в списке материнскую плату, положить ее в корзину (маленькая круглая красная кнопочка около товара).
     * 4. В левом боковом меню выбрать категорию корпуса - в подменю выбрать Aerocool
     * 5. Найти 4-й в списке корпус, положить его в корзину (маленькая круглая красная кнопочка около товара).
     * 6. Перейти в раздел корпуса - подменю Corsair
     * 6. Найти 10-й в списке корпус, кликнуть на ссылку-название (откроется страница товара).
     * 7. Нажать на красную кнопку "Добавить в корзину" справа от товара.
     * 8. Нажать на голубую кнопку "Перейти в корзину".
     * 9. Убедиться, что корзина не пустая, и что в ней содержаться ровно те наименования товаров, которые вы покупали.
     */

    @Test
    void testAddInBasket(){
        mainPage.open(); //1
        mainPage.goToCategory("Материнские платы", "Intel Socket 1200"); //2
        //3
        catalogPage.setAddress(mainPage.driver.getCurrentUrl());
        catalogPage.selectAndPutProduct(5);

        catalogPage.goToCategory("Корпуса", "AEROCOOL"); //4
        catalogPage.selectAndPutProduct(4);//5

        catalogPage.goToCategory("Корпуса", "CORSAIR"); //6.1
        catalogPage.openPageProduct(10);//6.2

        productPage.setAddress(catalogPage.driver.getCurrentUrl());
        productPage.addProductInBasket();//7
        productPage.goToBasket();//8

        Collections.sort(basketPage.getProductInBasket());//9
        Collections.sort(BasePage.listProductInBasket);//9

        assertTrue(fuzzyEqualityProductList());//9


    }

    /**
     * 1. Открыть главную страницу
     * 2. В левом боковом меню выбрать категорию материнские платы - в подменю выбрать Intel socket 1200
     * 3. Найти 8-ю в списке материнскую плату, положить ее в корзину (маленькая круглая красная кнопочка около товара).
     * 4. В левом боковом меню выбрать категорию корпуса - в подменю выбрать Aerocool
     * 5. Найти 7-й в списке корпус, положить его в корзину (маленькая круглая красная кнопочка около товара).
     * 6. Нажать на иконку корзины в правом верхнем углу.
     * 7. Удалить все товары из корзины
     * 8. Убедиться, что корзина пустая.
     */
    @Test
    void clearBasket(){
        mainPage.open(); //1
        mainPage.goToCategory("Материнские платы", "Intel Socket 1200"); //2
        //3
        catalogPage.setAddress(mainPage.driver.getCurrentUrl());
        catalogPage.selectAndPutProduct(8);

        catalogPage.goToCategory("Корпуса", "AEROCOOL"); //4
        catalogPage.selectAndPutProduct(7);//5
        catalogPage.openBasket();//6
        //7
        basketPage.clearBasket();
        basketPage.alertConfirm();
        assertNull(basketPage.getProductInBasket());//8
        basketPage.basketInEmpty();//8
    }

    /**
     * 1. Открыть главную страницу
     * 2. В поле поиска ввести "Asus"
     * 3. Нажать кнопку найти
     * 4. Проверить, что результат содержит как минимум 1 элемент
     * 5. Проверить, что в наименовании первого элемента содержится "Asus"
     */
    @Test
    void searchProduct(){
        mainPage.open();//1
        mainPage.search("Asus");//2 and 3
        catalogPage.setAddress(mainPage.driver.getCurrentUrl());
        assertTrue(catalogPage.checkProductSearch());
        assertTrue(catalogPage.checkNameProduct(1, "asus"));
    }

    public boolean fuzzyEqualityProductList(){
        if(basketPage.getProductInBasket().size() == BasePage.listProductInBasket.size()){
            int i = 0;
            for(String s: basketPage.getProductInBasket()){
                if(!BasePage.listProductInBasket.get(i).contains(s)){
                    return false;
                }else{
                    i++;
                }
            }
        }else{
            return false;
        }
        return true;
    }
}
