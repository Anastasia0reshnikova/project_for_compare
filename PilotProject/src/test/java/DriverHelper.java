import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

/**
 * Created by a.oreshnikova on 15.10.17.
 */

class DriverHelper {

    private WebDriver driver;

    void init() {
        System.out.println("Открываем браузер");
        //В папке resources два драйвера: для Win и Mac OS
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    void goToPage() {
        driver.get("https://mail.ru/");
    }

    void quit() {
        driver.quit();
        System.out.println("Браузер закрыт");
    }

    WebDriver driver() {
        return driver;
    }

}
