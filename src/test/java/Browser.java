import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.util.concurrent.TimeUnit;

public class Browser {
    private WebDriver driver;

    public WebDriver getWebDriver() {

        String nameBrowser;
        String yandexDriverPath = System.getProperty("yandexDriverPath");

        if (yandexDriverPath == null) {
            nameBrowser = "chrome";
        } else {
            nameBrowser = "yandex";
        };

        switch (nameBrowser) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
            case "yandex":
                System.setProperty("webdriver.chrome.driver", "src/main/resources/yandexdriver.exe");
                ChromeOptions options = new ChromeOptions();
                options.setBinary(System.getProperty("yandexDriverPath"));
                driver = new ChromeDriver(options);
                break;
            default:
                System.exit(1);
        }

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        return driver;
    }
}