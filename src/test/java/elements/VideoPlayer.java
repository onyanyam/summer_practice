package elements;

import base.BaseElement;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

/**
 * Класс-обёртка для видеоплеера Rutube.
 * Предоставляет методы управления воспроизведением (play/pause), настройками качества,
 * полноэкранным и кинотеатральным режимом.
 * Содержит ТОЛЬКО элементы, находящиеся внутри плеера.
 */
public class VideoPlayer extends BaseElement {

    private static final String PLAY_LABEL = "Воспроизвести";

    private static final String VIDEO_PLAYER_XPATH =
            "//div[contains(@class, 'touch-handlers-layout-module__layout')]";

    private static final String PLAY_BUTTON_XPATH =
            ".//button[@data-testid='ui-play']";

    private static final String SETTINGS_BUTTON_XPATH =
            ".//button[contains(@class, 'settings-module__settingBtn')]";

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

    public void pause() {
        waitForLoad();
        hover();

        SelenideElement playButton = baseElement.$x(PLAY_BUTTON_XPATH);
        if (playButton.exists() && !isPaused()) {
            playButton.click();
        }
    }

    public void play() {
        waitForLoad();
        hover();

        SelenideElement pauseButton = baseElement.$x(PLAY_BUTTON_XPATH);
        if (pauseButton.exists() && isPaused()) {
            pauseButton.click();
        }
    }

    public boolean isPaused() {
        String label = baseElement
                .$x(PLAY_BUTTON_XPATH)
                .getAttribute("aria-label");

        return label != null && label.contains(PLAY_LABEL);
    }

    public void setQuality(String quality) {
        baseElement.click();
        Selenide.sleep(1000);
        hover();
        SelenideElement settingsButton = baseElement.$x(SETTINGS_BUTTON_XPATH);
        settingsButton.shouldBe(visible).click();

        $x(QUALITY_MENU_ITEM_XPATH).shouldBe(visible).click();
        $x(String.format(QUALITY_OPTION_XPATH, quality)).shouldBe(visible).click();
    }

    public void toggleFullscreen() {
        SelenideElement fullscreenButton = baseElement.$x(FULLSCREEN_BUTTON_XPATH);
        fullscreenButton.click();
    }

    public void toggleCinemaMode() {
        SelenideElement cinemaButton = baseElement.$x(CINEMA_BUTTON_XPATH);
        cinemaButton.click();
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