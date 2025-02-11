package pageobject;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AccountProfilePage extends BasePage {
    //  Текст аздела страницы Профиль
    private static final String textAccountProfilePageExpected = "В этом разделе вы можете изменить свои персональные данные";

    // Локаторы
    // Текст Вход на странице входа
    private final String textAccountProfilePage = ".//p[text()='В этом разделе вы можете изменить свои персональные данные']";
    // Кнопка Выход на странице в Личном кабинете
    private final String logOutAccountProfilePage = ".//button[text()='Выход']";
    // LinkText в Конструктор из Личном кабинете
    private final String designerLinkText = ".//p[text()='Конструктор']";
    // LinkText Stellar Burgers
    private final String stellarBurgersLinkText = ".//*[name()='svg' and @xmlns='http://www.w3.org/2000/svg']";


    public AccountProfilePage(WebDriver driver) {
        super(driver);
    }

    // Методы
    // Метод проверки открытия страницы входа
    public void isAccountProfilePageOpen() {
        Assert.assertEquals(textAccountProfilePageExpected, driver.findElement(By.xpath(textAccountProfilePage)).getText());
    }

    // Нажать на кнопку Выход
    public void designerClick() {
        driver.findElement(By.xpath(designerLinkText)).click();
    }

    // Нажать на логотип Stellar Burgers
    public void stellaBurgersClick() {
        driver.findElement(By.xpath(stellarBurgersLinkText)).click();
    }

    // Нажать на кнопку Выход
    public void logInButtonClick() {
        driver.findElement(By.xpath(logOutAccountProfilePage)).click();
    }

}
