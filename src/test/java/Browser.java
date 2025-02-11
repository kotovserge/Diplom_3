import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Browser {

    public WebDriver getWebDriver(String browserName) {

        switch (browserName) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                return new ChromeDriver();
            case "yandex":
                System.setProperty("webdriver.chrome.driver", "src/main/resources/yandexdriver.exe");
                ChromeOptions options = new ChromeOptions();
                options.setBinary("C:/Users/serge/AppData/Local/Yandex/YandexBrowser/Application/browser.exe");
                return new ChromeDriver(options);
        }
        return null;
    }
}