import api.user.UserApi;
import api.user.UserLoginRequest;
import api.user.UserRandom;
import api.user.UserRegisterRequest;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pageobject.HomePage;
import pageobject.LoginPage;
import pageobject.RegisterPage;

import static java.util.Objects.isNull;
import static org.apache.commons.lang3.BooleanUtils.isTrue;

public class RegistrationTest {
    private WebDriver driver;
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
        driver = new Browser().getWebDriver();
        HomePage homePage = new HomePage(driver);
        driver.get(homePage.getURL());
    }

    @DisplayName("Успешная регистрация")
    @Description("Проверяем успешную регистрация")
    @Test
    public void registerTest() {
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.registration(userName, userEmail, userPassword);
        // Вход в аккаунт
        LoginPage loginPage = new LoginPage(driver);
        loginPage.logIn(userName, userPassword, userEmail);
    }

    @DisplayName("Регистрации с невалидным паролем")
    @Description("Проверяем регистрацию с невалидным паролем")
    @Test
    public void registerPasswordNegativeTest() {
        // Уменьшаем длину пароля до 4 символов
        userPassword = StringUtils.left(userPassword, 4);
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.registration(userName, userEmail, userPassword);
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
