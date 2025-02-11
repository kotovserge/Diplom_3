package pageobject;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ForgotPasswordPage extends BasePage {
    // Текст заголовка страницы восстановления пароля
    public static final String textForgotPasswordExpected = "Восстановление пароля";    // Текст Восстановление пароля на странице восстановление

    private final String textForgotPasswordPage = ".//h2[text()='Восстановление пароля']";

    // Кнопка Войти на старнице Восстановить пароль
    private final String logInButton = ".//a[text()='Войти']";


    public ForgotPasswordPage(WebDriver driver) {
        super(driver);
    }

    // Методы
    // Метод проверки открытия страницы восстановление пароля
    public void isForgotPasswordPageOpen() {
        Assert.assertEquals(textForgotPasswordExpected, driver.findElement(By.xpath(textForgotPasswordPage)).getText());
    }

    // Нажать на кнопку Войти
    public void logInButton() {
        driver.findElement(By.xpath(logInButton)).click();
    }

}
