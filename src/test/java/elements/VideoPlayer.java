package elements;

import base.BaseElement;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

/**
 * Класс-обёртка для видеоплеера Rutube.
 * Предоставляет методы управления воспроизведением (play/pause), настройками качества,
 * полноэкранным и кинотеатральным режимом.
 * Содержит ТОЛЬКО элементы, находящиеся внутри плеера.
 */
public class VideoPlayer extends BaseElement {

    private static final String PLAY_LABEL = "Воспроизвести видео (горячая клавиша K английская)";

    private static final String VIDEO_PLAYER_XPATH =
            "//div[contains(@class, 'touch-handlers-layout-module__layout')]";

    private static final String PLAY_BUTTON_XPATH =
            ".//button[@data-testid='ui-play']";

    private static final String SETTINGS_BUTTON_XPATH =
            ".//button[@aria-label='Открыть настройки видеоплеера']";

    private static final String QUALITY_OPTION_XPATH =
            "//*[contains(text(), '%s')]";

    private static final String FULLSCREEN_BUTTON_XPATH =
            ".//button[@data-testid='ui-fullscreen']";

    private static final String CINEMA_BUTTON_XPATH =
            ".//button[@data-testid='ui-cinema']";

    private static final String QUALITY_MENU_ITEM_XPATH =
            "//*[contains(text(), 'Качество')]";

    private VideoPlayer() {
        super(VIDEO_PLAYER_XPATH, "");
    }

    /**
     * Кликает по элементу через JavaScript (обходит перекрытия)
     */
    private void clickWithJS(SelenideElement element) {
        Selenide.executeJavaScript("arguments[0].click();", element);
    }

    /**
     * Показывает скрытый элемент через JavaScript
     */
    private void showElement(SelenideElement element) {
        Selenide.executeJavaScript(
                "arguments[0].style.display = 'block'; " +
                        "arguments[0].style.opacity = '1'; " +
                        "arguments[0].style.visibility = 'visible';",
                element
        );
    }

    public void pause() {
        waitForLoad();
        hover();

        SelenideElement playButton = baseElement.$x(PLAY_BUTTON_XPATH);
        if (playButton.exists() && !isPaused()) {
            playButton.click();
        }
    }

    public boolean isPaused() {
        String label = baseElement
                .$x(PLAY_BUTTON_XPATH)
                .getAttribute("aria-label");

        return label != null && label.contains(PLAY_LABEL);
    }

    /**
     * Открывает меню настроек плеера.
     */
    private void openSettingsMenu() {
        waitForLoad();

        hover();

        SelenideElement settingsBtn = baseElement.$x(SETTINGS_BUTTON_XPATH);
        settingsBtn.shouldBe(Condition.exist, Duration.ofSeconds(10));

        if (!settingsBtn.isDisplayed()) {
            showElement(settingsBtn);
        }

        clickWithJS(settingsBtn);
    }

    public void setQuality(String quality) {
        openSettingsMenu();

        $x(QUALITY_MENU_ITEM_XPATH).shouldBe(Condition.visible, Duration.ofSeconds(5)).click();
        $x(String.format(QUALITY_OPTION_XPATH, quality)).shouldBe(Condition.visible, Duration.ofSeconds(5)).click();

    }

    /**
     * Возвращает текущее качество видео.
     * Открывает настройки, читает активный пункт качества и закрывает настройки.
     */
    public String getCurrentQuality() {
        try {
            openSettingsMenu();

            SelenideElement anyQuality = $x("//*[contains(text(), '480p')]");
            if (anyQuality.exists()) {
                String quality = anyQuality.getText().trim();
                return quality;
            }

            return "";
        } catch (Exception e) {
            return "";
        }
    }

    public void toggleFullscreen() {
        hover();

        SelenideElement fullscreenButton = baseElement.$x(FULLSCREEN_BUTTON_XPATH);
        fullscreenButton.shouldBe(Condition.exist, Duration.ofSeconds(5));

        if (!fullscreenButton.isDisplayed()) {
            Selenide.executeJavaScript(
                    "arguments[0].style.display = 'block'; " +
                            "arguments[0].style.opacity = '1'; " +
                            "arguments[0].style.visibility = 'visible';",
                    fullscreenButton
            );
        }

        Selenide.executeJavaScript("arguments[0].click();", fullscreenButton);
    }


    public void waitForLoad() {
        baseElement.shouldBe(visible);
    }

    public void hover() {
        baseElement.hover();
    }

    public static VideoPlayer getPlayer() {
        return new VideoPlayer();
    }
}