import api.user.UserApi;
import api.user.UserLoginRequest;
import api.user.UserRandom;
import api.user.UserRegisterRequest;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import pageobject.HomePage;
import pageobject.LoginPage;
import pageobject.RegisterPage;
import java.util.concurrent.TimeUnit;

import static java.util.Objects.isNull;
import static org.apache.commons.lang3.BooleanUtils.isTrue;

@RunWith(Parameterized.class)
public class RegistrationTest {
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
        driver = new Browser().getWebDriver(browserName);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        HomePage homePage = new HomePage(driver);
        driver.get(homePage.getURL());
    }

    public RegistrationTest(String browserName) {
        this.browserName = browserName;
    }

    @Parameterized.Parameters(name = " ({0})")
    public static Object[][] testDate() {
        return new Object[][]{
                {"chrome"},
                {"yandex"}
        };
    }

    // Успешная регистрация
    @Step("Регистрация нового пользователя")
    @Test
    public void registerTest() {
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
        loginPage.logIn(userName, userPassword, userEmail);
    }

    // Регистрации с невалидным паролем
    @Step("Регистрация с невалидным паролем ")
    @Test
    public void registerPasswordNegativeTest() {
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
        // Уменьшаем длину пароля до 4 символов
        userPassword = StringUtils.left(userPassword, 4);
        // Заполнить поле Name
        registerPage.fillName(userName);
        // Заполнить поле Email
        registerPage.fillEmail(userEmail);
        // Заполнить поле Пароль
        registerPage.fillPassword(userPassword);
        // Нажать кнопку Зарегистрироваться
        registerPage.registerButtonClick();
        // Проверка ошибки при вводе невалидного пароля
        registerPage.isErrorPassword();
    }

    @After
    public void teardown() {
        driver.quit();
        // Авторизация пользователя для получения токена
        UserLoginRequest userLoginRequest = new UserLoginRequest(userEmail, userPassword);
        response = userApi.login(userLoginRequest);
        if (isTrue(response.extract().path("success"))) {
            // Сохраним токен для последующего удаления пользователя
            accessToken = response.extract().path("accessToken");
        }
        // Удаление пользователя
        if ( !isNull(accessToken)) {
            userApi.delete(accessToken);
        }
    }
}
