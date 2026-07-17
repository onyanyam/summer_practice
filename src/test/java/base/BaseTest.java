package base;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.Cookie;
import utils.Logger;

import java.io.File;
import java.time.Duration;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

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
    public void setUp(TestInfo testInfo) {
        Logger.testStart(testInfo.getDisplayName());

        Configuration.browser = "chrome";
        Configuration.browserSize = "1920x1080";
        Configuration.timeout = 15000;
        Configuration.pageLoadTimeout = 60000;
        Configuration.headless = false;

        Logger.info("Открываем страницу RuTube...");

        open("https://rutube.ru");

        closePopups();

        loadCookiesFromFile("src/test/resources/cookies.json");

        refresh();
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

    private void loadCookiesFromFile(String filePath) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode cookies = mapper.readTree(new File(filePath));
            for (JsonNode node : cookies) {
                Cookie cookie = new Cookie(
                        node.get("name").asText(),
                        node.get("value").asText(),
                        node.get("domain").asText(),
                        node.get("path").asText(),
                        null
                );
                WebDriverRunner.getWebDriver().manage().addCookie(cookie);
            }
        } catch (Exception e) {
            System.err.println("Ошибка при чтении кук: " + e.getMessage());
        }
    }

    @AfterEach
    public void tearDown(TestInfo testInfo) {
        Logger.testEnd(testInfo.getDisplayName());

        Selenide.closeWebDriver();
    }
}