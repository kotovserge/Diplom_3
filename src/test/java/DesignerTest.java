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
public class DesignerTest {
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

    public DesignerTest(String browserName) {
        this.browserName = browserName;
    }

    @Parameterized.Parameters(name = " ({0})")
    public static Object[][] testDate() {
        return new Object[][]{
                {"chrome"},
                {"yandex"}
        };
    }

    // Конструктор, проверка раздела Булки
    @Step("Конструктор, переход к разделу Булки")
    @Test
    public void designerRollsTest() {
        // Создать объект с домашней страницей
        HomePage homePage = new HomePage(driver);
        // Нажать кнопку Войти в акаунт на главной
        homePage.accountButtonClick();
        // Создать объект со страницей Login
        LoginPage loginPage = new LoginPage(driver);
        // Заполнить форму данными и нажать Войти
        loginPage.logIn(userName, userPassword, userEmail);
        // Нажать в конструкторе на Булки
        homePage.designerRollsLinkTextClick();
        // Проверить раздел Булки
        homePage.isDesignerRollsLinkText();
    }

    // Конструктор, проверка раздела соусы
    @Step("Конструктор, переход к разделу Суосы")
    @Test
    public void designerSauceTest() {
        // Создать объект с домашней страницей
        HomePage homePage = new HomePage(driver);
        // Нажать кнопку Войти в акаунт на главной
        homePage.accountButtonClick();
        // Создать объект со страницей Login
        LoginPage loginPage = new LoginPage(driver);
        // Заполнить форму данными и нажать Войти
        loginPage.logIn(userName, userPassword, userEmail);

        // Нажать в конструкторе на Соусы
        homePage.designerSauceLinkTextClick();
        // Проверить раздел Соусы
        homePage.isDesignerSauceLinkText();
    }

    // Конструктор, проверка раздела Начинки
    @Step("Конструктор, переход к разделу Начинки")
    @Test
    public void designerFillinfTest() {
        // Создать объект с домашней страницей
        HomePage homePage = new HomePage(driver);
        // Нажать кнопку Войти в акаунт на главной
        homePage.accountButtonClick();
        // Создать объект со страницей Login
        LoginPage loginPage = new LoginPage(driver);
        // Заполнить форму данными и нажать Войти
        loginPage.logIn( userName, userPassword, userEmail);
        // Нажать в конструкторе на Начинки
        homePage.designerFillingLinkTextClick();
        // Проверить раздел Начинки
        homePage.isDesignerFillingLinkText();
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
