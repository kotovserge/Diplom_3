package pageobject;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static dev.failsafe.internal.util.Assert.isTrue;

public class HomePage extends BasePage {

    // Текст кнопки Оформить заказ при успешной регистрации на главной стринице
    private static final String textPlaceOrderButtonExpected = "Оформить заказ";

    // Локаторы
    // URL Stellar Burgers
    private final String url = "https://stellarburgers.nomoreparties.site/";
    // Кнопка Войти в аккаунт на странице
    private final String accountButton = ".//button[text()='Войти в аккаунт']";
    // Кнопка Личный кабинет в header
    private final String accountHeaderButon = ".//p[text()='Личный Кабинет']";
    // Кнопка Оформить Заказ
    private final String placeOrderButton = ".//button[text()='Оформить заказ']";
    // Раздел Собери бургер - Булки
    private final String designerRollsLinkText = ".//span[text()='Булки']";
    // Раздел Собери бургер - Соусы
    private final String designerSauceLinkText = ".//span[text()='Соусы']";
    // Раздел Собери бургер - Начинки
    private final String designerFillingLinkText = ".//span[text()='Начинки']";
    //
    private final String designerRollsSelect = ".//div[@class='tab_tab__1SPyG tab_tab_type_current__2BEPc pt-4 pr-10 pb-4 pl-10 noselect']/./span[text()='Булки']";
    private final String designerSauceSelect = ".//div[@class='tab_tab__1SPyG tab_tab_type_current__2BEPc pt-4 pr-10 pb-4 pl-10 noselect']/./span[text()='Соусы']";
    private final String designerFillingSelect = ".//div[@class='tab_tab__1SPyG tab_tab_type_current__2BEPc pt-4 pr-10 pb-4 pl-10 noselect']/./span[text()='Начинки']";

    public HomePage(WebDriver driver) {
        super(driver);
    }

    // МЕТОДЫ

    // Получить URL
    public String getURL() {
        return url;
    }

    // Клик по кнопке Войти в аккаунт на странице
    public void accountButtonClick() {
        driver.findElement(By.xpath(accountButton)).click();
    }

    // Клик по кнопке Личный Кабинет в header
    public void accountHeaderButtonClick() {
        driver.findElement(By.xpath(accountHeaderButon)).click();
    }

    // Метод проверки наличия кнопки Оформить заказ
    public void isPlaceOrderButton() {
        Assert.assertEquals(textPlaceOrderButtonExpected, driver.findElement(By.xpath(placeOrderButton)).getText());
    }

    // Нажать LinkText Булки
    public void designerRollsLinkTextClick() {
        // По умолчанию открывается раздел Булки
        // поэтому уходим из раздела Булки
        designerFillingLinkTextClick();
        // Возвращаемся в раздел Булки
        driver.findElement(By.xpath(designerRollsLinkText)).click();
    }
    // Проверить раздел LinkText Булки
    public void isDesignerRollsLinkText() {
       Assert.assertEquals("Булки", driver.findElement(By.xpath(designerRollsLinkText)).getText());
    }

    // Нажать LinkText Соусы
    public void designerSauceLinkTextClick() {
        driver.findElement(By.xpath(designerSauceLinkText)).click();
    }
    // Проверить раздел LinkText Соусы
    public void isDesignerSauceLinkText() {
        Assert.assertEquals("Соусы", driver.findElement(By.xpath(designerSauceLinkText)).getText());
    }

    // Нажать LinkText Начинки
    public void designerFillingLinkTextClick() {
        driver.findElement(By.xpath(designerFillingLinkText)).click();
    }
    // Проверить раздел LinkText Filling
    public void isDesignerFillingLinkText() {
        Assert.assertEquals("Начинки", driver.findElement(By.xpath(designerFillingLinkText)).getText());
    }

}
