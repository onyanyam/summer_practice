package elements;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class VideoPlayer extends BaseElement {

    public VideoPlayer() {
        super("//div[contains(@class, 'touch-handlers-layout-module__layout')]", "");
    }

    public void pause() {
        waitForLoad();
        hover();

        SelenideElement playButton = baseElement.$x(".//button[@data-testid='ui-play']");
        if (playButton.exists() && !isPaused()) {
            playButton.click();
        }
    }

    public void play() {
        waitForLoad();
        hover();

        SelenideElement pauseButton = baseElement.$x(".//button[@data-testid='ui-play']");
        if (pauseButton.exists() && isPaused()) {
            pauseButton.click();
        }
    }

    public boolean isPaused() {
        String label = baseElement
                .$x(".//button[@data-testid='ui-play']")
                .getAttribute("aria-label");

        return label != null && label.contains("Воспроизвести");
    }

    public void setQuality(String quality) {
        SelenideElement settingsButton = baseElement.$x(".//button[contains(@class, 'settings-module__settingBtn')]");
        settingsButton.click();
        SelenideElement qualityOption = $x(String.format("//span[contains(text(), '%s')]", quality));
        qualityOption.click();
    }

    public void toggleFullscreen() {
        SelenideElement fullscreenButton = baseElement.$x(".//button[@data-testid='ui-fullscreen']");
        fullscreenButton.click();
    }

    public void toggleCinemaMode() {
        SelenideElement cinemaButton = baseElement.$x(".//button[@data-testid='ui-cinema']");
        cinemaButton.click();
    }

    public void share() {
        SelenideElement shareButton = Button.byXpath("//button[.//span[contains(text(), 'Поделиться')]]").getBaseElement();
        shareButton.click();
    }

    public void openMenu() {
        SelenideElement menuButton = baseElement.$x(".//button[@data-testid='menu-action-video-row']");
        menuButton.click();
    }

    public void waitForLoad() {
        baseElement.shouldBe(visible);
    }

    public void hover(){
        baseElement.hover();
    }
}