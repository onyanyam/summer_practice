package base;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import java.time.Duration;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;

/**
 * Базовый класс для всех тестов проекта.
 * Отвечает за настройку драйвера, открытие Rutube перед каждым тестом,
 * закрытие всплывающих окон и корректное завершение сессии браузера после теста.
 */
public class BaseTest {

    private static final String COOKIE_BUTTON_XPATH =
            "//div[contains(@class, 'cookie')]//button";

    private static final String POPUP_CLOSE_BUTTON_XPATH =
            "//div[contains(@class, 'wdp-popup-module__popup')]//*[contains(@class, 'close')]";

    @BeforeEach
    public void setUp() {
        Configuration.browser = "chrome";
        Configuration.browserSize = "1920x1080";
        Configuration.timeout = 15000;
        Configuration.pageLoadTimeout = 60000;
        Configuration.headless = false;

        open("https://rutube.ru");

        closePopups();
    }

    public void closePopups() {
        if ($x(COOKIE_BUTTON_XPATH).isDisplayed()) {
            $x(COOKIE_BUTTON_XPATH).click();
        }

        try {
            $x(POPUP_CLOSE_BUTTON_XPATH)
                    .shouldBe(visible, Duration.ofSeconds(2))
                    .click();
        } catch (Exception e) {
        }
    }

    @AfterEach
    public void tearDown() {
        Selenide.closeWebDriver();
    }
}