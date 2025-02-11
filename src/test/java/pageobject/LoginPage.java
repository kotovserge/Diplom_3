package pageobject;

import com.google.common.io.Resources;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {
    //  Текст заголовка странцы Входа (авторизации)
    private static final String textLoginPageExpected = "Вход";
//    // Текст кнопки Оформить зазка при успешной регистрации на главной стринице
//    private static final String textPlaceOrderButton = "Оформить заказ";

    // Локаторы
    // Текст Вход на странице входа
    private final String textLoginPage = ".//h2[text()='Вход']";
    // Link text Зарегистрироваться
    private final String registerLinkText = ".//a[text()='Зарегистрироваться']";
    // Поле ввода Email
    private final String emailField = ".//input[@name='name']";
    // Поле ввода Email
    private final String passwordField = ".//input[@name='Пароль']";
    // Кнопка Войти
    private final String logInButton = ".//button[text()='Войти']";
    // Кнопка Восстановить пароль странице входа
    private final String forgotPasswordButton = ".//a[text()='Восстановить пароль']";

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    // Методы
    // Метод проверки открытия страницы входа
    public void isLoginPageOpen() {
        Assert.assertEquals(textLoginPageExpected, driver.findElement(By.xpath(textLoginPage)).getText());
    }

    // Нажать на текст Зарегистрироваться
    public void registerLinkTextClick() {
        driver.findElement(By.xpath(registerLinkText)).click();
    }

    // Заполнить поля Email
    public void fillEmail(String userEmail) {
        driver.findElement(By.xpath(emailField)).sendKeys(userEmail);
    }

    // Заполнить поле Пароль
    public void fillPassword(String userPassword) {
        driver.findElement(By.xpath(passwordField)).sendKeys(userPassword);
    }

    // Нажать на кнопку Восстановить пароль
    public void forgotPasswordButtonClick() {
        driver.findElement(By.xpath(forgotPasswordButton)).click();
    }

    // Нажать на кнопку Восстановить пароль
    public void logInButtonClick() {
        driver.findElement(By.xpath(logInButton)).click();
    }

    public void logIn(String userName, String userPassword, String userEmail  ) {
        // Создать объект со страницей LoginPage
        LoginPage loginPage = new LoginPage(driver);
        // Проверить открытие страницы Входа в аккаунт
        loginPage.isLoginPageOpen();
        // Заполнить поле Email
        loginPage.fillEmail(userEmail);
        // Заполнить поле Пароль
        loginPage.fillPassword(userPassword);
        // Нажать кнопку Войти
        loginPage.logInButtonClick();
        // Создать объект с домашней страницей
        HomePage homePage = new HomePage(driver);
        // Проверка успешного входа - наличие кнопки Оформить заказ
        homePage.isPlaceOrderButton();

    }
}
