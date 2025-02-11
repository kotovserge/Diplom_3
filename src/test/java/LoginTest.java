import api.user.UserApi;
import api.user.UserLoginRequest;
import api.user.UserRandom;
import api.user.UserRegisterRequest;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import pageobject.ForgotPasswordPage;
import pageobject.HomePage;
import pageobject.LoginPage;
import pageobject.RegisterPage;

import java.util.concurrent.TimeUnit;

import static java.util.Objects.isNull;
import static org.apache.commons.lang3.BooleanUtils.isTrue;

@RunWith(Parameterized.class)
public class LoginTest {
    private WebDriver driver;
    private String browserName;

    private UserApi userApi;
    private UserRegisterRequest userDataRegister;
    private ValidatableResponse response;
    private String accessToken;
    public String userEmail;
    public String userPassword;
    public String userName;

    @Before
    public void prepare() {
        userApi = new UserApi();
        // Генерация данных пользователя
        userDataRegister = new UserRandom().generateUser();
        userEmail = userDataRegister.getEmail();
        userPassword = userDataRegister.getPassword();
        userName = userDataRegister.getName();

        // Создание пользователя
        response = userApi.register(userDataRegister);
        // Сохраним токен для последующего удаления пользователя
        if (isTrue(response.extract().path("success"))) {
            // Сохраним токен для последующего удаления пользователя
            accessToken = response.extract().path("accessToken");
        }

        driver = new Browser().getWebDriver(browserName);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        HomePage homePage = new HomePage(driver);
        driver.get(homePage.getURL());
    }

    public LoginTest(String browserName) {
        this.browserName = browserName;
    }

    @Parameterized.Parameters(name = " ({0})")
    public static Object[][] testDate() {
        return new Object[][]{
                {"chrome"},
                {"yandex"}
        };
    }

    // Вход в аккаунт по кнопке Войти в аккаунт на главной
    @Step("Вход в аккаунт по кнопке Войти в аккаунт на главной")
    @Test
    public void loginHomePageTest() {
        // Создать объект с домашней страницей
        HomePage homePage = new HomePage(driver);
        // Нажать кнопку Войти в акаунт на главной
        homePage.accountButtonClick();
        // Создать объект со страницей Login
        LoginPage loginPage = new LoginPage(driver);
        // Заполнить форму данными и нажать Войти
        loginPage.logIn( userName, userPassword, userEmail);
    }

    // Вход в аккаунт через кнопку Личный кабинет
    @Step("Вход в аккаунт через кнопку Личный кабинет")
    @Test
    public void loginHeaderHomePageTest() {
        // Создать объект с домашней страницей
        HomePage homePage = new HomePage(driver);
        // Нажать кнопку Личный кабинет в header
        homePage.accountHeaderButtonClick();
        LoginPage loginPage = new LoginPage(driver);
        // Заполнить форму данными и нажать Войти
        loginPage.logIn( userName, userPassword, userEmail);
    }

    // Вход в аккаунт через форму регистрации
    @Step("Вход в аккаунт через форму регистрации")
    @Test
    public void loginRegisterPageTest() {
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
        // Нажать кнопку Войти на старнице регистрация
        registerPage.logInButtonClick();
        loginPage.logIn( userName, userPassword, userEmail);
    }


    // Вход в аккаунт через форму Восстановить пароль
    @Step("Вход в аккаунт через кнопку в форме Восстановления пароля")
    @Test
    public void loginForgotPasswordPageTest() {
        // Создать объект с домашней страницей
        HomePage homePage = new HomePage(driver);
        // Нажать кнопку Заказать на чердаке
        homePage.accountHeaderButtonClick();
        // Создать объект со страницей LoginPage
        LoginPage loginPage = new LoginPage(driver);
        // Проверить открытие страницы Входа в аккаунт
        loginPage.isLoginPageOpen();
        // Войти в Востановление пароля
        loginPage.forgotPasswordButtonClick();
        // Создать объект со страницей ForgotPassword
        ForgotPasswordPage forgotPasswordPage = new ForgotPasswordPage(driver);
        // Проверить открытие страницы Восстановление пароля
        forgotPasswordPage.isForgotPasswordPageOpen();
        // Нажать кнопку Войти на старнице Восстанoвления пароля
        forgotPasswordPage.logInButton();
        loginPage.logIn( userName, userPassword, userEmail);
    }

    @After
    public void teardown() {
        driver.quit();

        // Удаление пользователя
        if ( !isNull(accessToken)) {
            userApi.delete(accessToken);
        }

    }
}
