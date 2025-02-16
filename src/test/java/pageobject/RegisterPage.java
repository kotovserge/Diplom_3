package pageobject;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegisterPage extends BasePage {
    // Текст заголовка страницы Регистрации
    public static final String textRegisterPageExpected = "Регистрация";
    // Текст ошибки ввода невалидного пароля
    public static final String textErrorPasswordExpected = "Некорректный пароль";

    // Локаторы
    // Текст регитсрация на странице входа
    private final String textRegisterPage = ".//h2[text()='Регистрация']";
    // Поле ввода Имя
    private final String nameField = ".//label[text()='Имя']/../input";
    // Поле ввода Email
    private final String emailField = ".//label[text()='Email']/../input";
    // Поле ввода Пароль
    private final String passwordField = ".//input[@name='Пароль']";
    // Кнопка Зарегистрироваться
    private final String registerButton = ".//button[text()='Зарегистрироваться']";
    // Текст ошибки при вводе невалидного пароля
    private  final String textErrorPassword = ".//p[text()='Некорректный пароль']";
    // Кнопка Войти на странице регистрация
    private  final String logInButton= ".//a[text()='Войти']";

    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    // Методы
    @Step("Проверка открытия страницы регистрации")
    public void isRegisterPageOpen() {
        Assert.assertEquals(textRegisterPageExpected, driver.findElement(By.xpath(textRegisterPage)).getText());
    }

    @Step("Заполнить поле Имя")
    public void fillName(String userName) {
        driver.findElement(By.xpath(nameField)).sendKeys(userName);
    }

    @Step("Заполнить поле Email")
    public void fillEmail(String userEmail) {
        driver.findElement(By.xpath(emailField)).sendKeys(userEmail);
    }

    @Step("Заполнить поле Пароль")
    public void fillPassword(String userPassword) {
        driver.findElement(By.xpath(passwordField)).sendKeys(userPassword);
    }

    @Step("Нажать на кнопку Зарегистрироваться")
    public void registerButtonClick() {
        driver.findElement(By.xpath(registerButton)).click();
    }

    @Step("Проверить текст ошибки при вводе невалидного пароля")
    public void isErrorPassword() {
        Assert.assertEquals(textErrorPasswordExpected, driver.findElement(By.xpath(textErrorPassword)).getText());
    }

    @Step("Нажать кнопку Войти на странице регистрация")
    public void logInButtonClick() {
        driver.findElement(By.xpath(logInButton)).click();
    }

    // Регистрация нового пользователя
    public void registration( String userName, String userEmail, String userPassword) {
        // Создать объект с домашней страницей
        HomePage homePage = new HomePage(driver);
        // Нажать кнопку Заказать на чердаке
        homePage.accountHeaderButtonClick();
        // Создать объект со страницей LoginPage
        LoginPage loginPage = new LoginPage(driver);
        // Проверить открытие страницы Входа в аккаунт
        loginPage.isLoginPageOpen();
        // Войти в регистрацию
        loginPage.registerLinkTextClick();
        // Создать объект со страницей RegisterPage
        RegisterPage registerPage = new RegisterPage(driver);
        // Проверить открытие страницы Регистрация
        registerPage.isRegisterPageOpen();
        // Заполнить поле Name
        registerPage.fillName(userName);
        // Заполнить поле Email
        registerPage.fillEmail(userEmail);
        // Заполнить поле Пароль
        registerPage.fillPassword(userPassword);
        // Нажать кнопку Зарегистрироваться
        registerPage.registerButtonClick();
    }
}
