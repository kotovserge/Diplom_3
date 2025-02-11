package pageobject;

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
    // Метод проверки открытия страницы регистрации
    public void isRegisterPageOpen() {
        Assert.assertEquals(textRegisterPageExpected, driver.findElement(By.xpath(textRegisterPage)).getText());
    }

    // Заполнить поле Имя
    public void fillName(String userName) {
        driver.findElement(By.xpath(nameField)).sendKeys(userName);
    }

    // Заполнить поля Email
    public void fillEmail(String userEmail) {
        driver.findElement(By.xpath(emailField)).sendKeys(userEmail);
    }

    // Заполнить поле Пароль
    public void fillPassword(String userPassword) {
        driver.findElement(By.xpath(passwordField)).sendKeys(userPassword);
    }

    // Нажать на кнопку Зарегистрироваться
    public void registerButtonClick() {
        driver.findElement(By.xpath(registerButton)).click();
    }

    // Проверить текст ошибки при вводе невалидного пароля
    public void isErrorPassword() {
        Assert.assertEquals(textErrorPasswordExpected, driver.findElement(By.xpath(textErrorPassword)).getText());
    }

    // Нажать кнопку Войти на странице регистрация
    public void logInButtonClick() {
        driver.findElement(By.xpath(logInButton)).click();
    }
}
