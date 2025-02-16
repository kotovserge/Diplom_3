import api.user.UserApi;
import api.user.UserRandom;
import api.user.UserRegisterRequest;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pageobject.AccountProfilePage;
import pageobject.HomePage;
import pageobject.LoginPage;

import static java.util.Objects.isNull;
import static org.apache.commons.lang3.BooleanUtils.isTrue;

public class PersonalAccountTest {
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
        // Создание пользователя
        response = userApi.register(userDataRegister);
        // Сохраним токен для последующего удаления пользователя
        if (isTrue(response.extract().path("success"))) {
            // Сохраним токен для последующего удаления пользователя
            accessToken = response.extract().path("accessToken");
        }

        driver = new Browser().getWebDriver();
        HomePage homePage = new HomePage(driver);
        driver.get(homePage.getURL());
    }


    @DisplayName("Переход в ЛК после регистрации пользователя")
    @Description("Проверяем переход в ЛК после регистрации пользователя")
    @Test
    public void inPersonalAccountPageTest() {
        // Создать объект с домашней страницей
        HomePage homePage = new HomePage(driver);
        // Нажать кнопку Войти в акаунт на главной
        homePage.accountButtonClick();
        // Создать объект со страницей Login
        LoginPage loginPage = new LoginPage(driver);
        loginPage.logIn( userName, userPassword, userEmail);
        // Перейти в личный кабинет по ссылке в header
        homePage.accountHeaderButtonClick();
        // Создать объект Личный кабинет
        AccountProfilePage accountProfilePage = new AccountProfilePage(driver);
        // Проверить открытие страницы Личного кабинета
        accountProfilePage.isAccountProfilePageOpen();
    }

    @DisplayName("Переход в Конструктор из Личного Кабинета")
    @Description("Проверяем переход в Конструктор из Личного Кабинета")
    @Test
    public void designerTest() {
        // Создать объект с домашней страницей
        HomePage homePage = new HomePage(driver);
        // Нажать кнопку Войти в акаунт на главной
        homePage.accountButtonClick();
        // Создать объект со страницей Login
        LoginPage loginPage = new LoginPage(driver);
        loginPage.logIn( userName, userPassword, userEmail);
        // Перейти в личный кабинет по ссылке в header
        homePage.accountHeaderButtonClick();
        // Создать объект Личный кабинет
        AccountProfilePage accountProfilePage = new AccountProfilePage(driver);
        // Проверить открытие страницы Личного кабинета
        accountProfilePage.isAccountProfilePageOpen();
        // Нажать текстовую ссылку перехода в конструктор
        accountProfilePage.designerClick();
    }

    @DisplayName("Переход по логотипу Stellar Burger")
    @Description("Проверяем переход по логотипу Stellar Burger")
    @Test
    public void logoTest() {
        // Создать объект с домашней страницей
        HomePage homePage = new HomePage(driver);
        // Нажать кнопку Войти в акаунт на главной
        homePage.accountButtonClick();
        // Создать объект со страницей Login
        LoginPage loginPage = new LoginPage(driver);
        loginPage.logIn( userName, userPassword, userEmail);
        // Перейти в личный кабинет по ссылке в header
        homePage.accountHeaderButtonClick();
        // Создать объект Личный кабинет
        AccountProfilePage accountProfilePage = new AccountProfilePage(driver);
        // Проверить открытие страницы Личного кабинета
        accountProfilePage.isAccountProfilePageOpen();
        // Нажать логотип StellaBergers
        accountProfilePage.stellaBurgersClick();
        // Проверка открытия домашней страницы
        homePage.isPlaceOrderButton();
    }

    @DisplayName("Выход их аккаунта")
    @Description("Проверяем Выход их аккаунта")
    @Test
    public void exitAccountTest() {
        // Создать объект с домашней страницей
        HomePage homePage = new HomePage(driver);
        // Нажать кнопку Войти в акаунт на главной
        homePage.accountButtonClick();
        // Создать объект со страницей Login
        LoginPage loginPage = new LoginPage(driver);
        loginPage.logIn( userName, userPassword, userEmail);
        // Перейти в личный кабинет по ссылке в header
        homePage.accountHeaderButtonClick();
        // Создать объект Личный кабинет
        AccountProfilePage accountProfilePage = new AccountProfilePage(driver);
        // Проверить открытие страницы Личного кабинета
        accountProfilePage.isAccountProfilePageOpen();
        // Нажать кнопку Выход в личном кабинете
        accountProfilePage.logInButtonClick();
        // Проверить выход из Личного кабинета
        loginPage.isLoginPageOpen();
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