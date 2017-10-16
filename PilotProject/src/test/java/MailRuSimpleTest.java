import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by a.oreshnikova on 15.10.17.
 */

public class MailRuSimpleTest {

    private static DriverHelper dHelper;
    static WebDriverWait wait;

    @BeforeClass
    public static void start() {
        dHelper = new DriverHelper();
        dHelper.init();
        wait = new WebDriverWait(dHelper.driver(), 20);
    }

    //Перейти на сайт mail.ru и проверить кнопку НАЙТИ
    @Test
    public void goToPageAndCheckButton_Test() {
        dHelper.goToPage();
        //Найти кнопку "Найти"
        WebElement element = dHelper.driver().findElement(By.cssSelector("[id='search:submit']"));
        String text = element.getAttribute("value");
        //Проверки JUnit
        Assert.assertEquals("Ошибка в тексте кнопки", text, "Найти");
        Assert.assertNotEquals(text, "Войти");
        Assert.assertTrue("Кнопка не отображается", element.isDisplayed());
        //Проверки Assert-J
        assertThat(text)
                .as("Ошибка в тексте кнопки")
                .isEqualTo("Найти")
                .isNotEqualTo("Войти");
        assertThat(element.isDisplayed())
                .as("Кнопка не отображается")
                .isTrue();
    }

    //Перейти на сайт mail.ru и проверить меню сайта
    @Test
    public void checkNewsMenu_Test() {
        dHelper.goToPage();
        //Собрать текст пунктов меню в список
        List<String> menu_fact =
                Arrays.asList("Поиск в интернете", "Картинки", "Видео", "Приложения", "Ответы");
        List<String> menu_exp = new ArrayList<String>();
        List<WebElement> elements = dHelper.driver().findElements(By.cssSelector("[id='search__categories'] a"));
        for(WebElement element: elements) {
            menu_exp.add(element.getText());
        }
        //Проверки JUnit
        Assert.assertFalse("Список меню пустой.", menu_exp.isEmpty());
        Assert.assertEquals("Ошибки в меню.", menu_exp, menu_fact);
        Assert.assertEquals("Ошибка в количестве элементов", menu_exp.size(), 5);
        //Проверки Assert-J
        assertThat(menu_exp)
                .as("Список меню пустой")
                .isNotEmpty()
                .as("Список меню не соответствует ожидаемому " + menu_fact)
                .isEqualTo(menu_fact)
                .as("Не верный размер списка")
                .hasSize(5)
                .as("Список состоит из дополнительных элементов")
                .containsOnlyElementsOf(menu_fact)
                .as("Список содержит лишний элемент")
                .doesNotContain("Найти");
    }

    //Перейти на сайт mail.ru и проверить поиск
    @Test
    public void checkSearchWeather_Test() {
        dHelper.goToPage();
        //Ввести в строку поиска слово "погода"
        dHelper.driver().findElement(By.cssSelector("[id='q']")).click();
        dHelper.driver().findElement(By.cssSelector("[id='q']")).sendKeys("погода");
        dHelper.driver().findElement(By.cssSelector("[id='search:submit']")).click();
        //Найти первые 11 результатов и получить их текст
        List<String> title = new ArrayList<String>();
        List<WebElement> elements = dHelper.driver().findElements(By.cssSelector(".result__title a"));
        for (WebElement element: elements) {
            title.add(element.getText());
        }
        //Проверить, что список результатов не пуст и проверить первые 3 результата
        //Проверки JUnit
        Assert.assertFalse("Список пуст", title.isEmpty());
        Assert.assertEquals("Ошибка в первом результате",
                title.get(0), "Санкт-Петербург — Погода Mail.Ru");
        Assert.assertEquals("Ошибка во втором результате",
                title.get(1), "GISMETEO: погода в Санкт-Петербурге сегодня ― прогноз погоды на...");
        Assert.assertEquals("Ошибка в третьем результате",
                title.get(2), "Прогноз погоды в Санкт-Петербурге на 10 дней — Яндекс.Погода");
        Assert.assertEquals("Ошибка размере сиска результатов",
                title.size(), 11);
        //Проверки Assert-J
        assertThat(title)
                .as("Список результатов пуст")
                .isNotEmpty()
                .as("Не верный размер списка результатов")
                .hasSize(11)
                .as("Список не содержит ожидаемый результат")
                .contains("Санкт-Петербург — Погода Mail.Ru",
                        "GISMETEO: погода в Санкт-Петербурге сегодня ― прогноз погоды на...",
                        "Прогноз погоды в Санкт-Петербурге на 10 дней — Яндекс.Погода")
                .as("Список содержит ошибочный результат")
                .doesNotContain("Фильмы и музыка");
        assertThat(title.get(0))
                .as("Первый результат поиска не соответствует ожидаемому")
                .isEqualTo("Санкт-Петербург — Погода Mail.Ru");
    }

    @AfterClass
    public static void tearDown() {
        dHelper.quit();
    }
}
