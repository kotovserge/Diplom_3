import api.user.UserApi;
import api.user.UserRandom;
import api.user.UserRegisterRequest;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import pageobject.AccountProfilePage;
import pageobject.HomePage;
import pageobject.LoginPage;
import java.util.concurrent.TimeUnit;

import static java.util.Objects.isNull;
import static org.apache.commons.lang3.BooleanUtils.isTrue;

@RunWith(Parameterized.class)
public class PersonalAccountTest {
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

    public PersonalAccountTest(String browserName) {
        this.browserName = browserName;
    }

    @Parameterized.Parameters(name = " ({0})")
    public static Object[][] testDate() {
        return new Object[][]{
                {"chrome"},
                {"yandex"}
        };
    }

    // Переход в ЛК после регистрации пользователя
    @Step("Переход по клику на Личный кабинет")
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

    // Переход в Конструктор из Личного Кабинета
    @Step("Переход из Личного кабинета в Конструктор")
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

    // Переход по логотипу Stellar Burger
    @Step("Переход из Личного кабинета по логотипу Stellar Burger")
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

    // Выход их аккаунта
    @Step("Выход из аккаунта")
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