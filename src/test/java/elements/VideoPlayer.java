package elements;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public class VideoPlayer extends BaseElement {

    public VideoPlayer() {
        super("//div[contains(@class, 'touch-handlers-layout-module__layout')]", "");
    }

    public void pause() {
        SelenideElement playButton = baseElement.$x(".//button[@data-testid='ui-play']");
        if (playButton.exists()) {
            playButton.click();
        }
    }

    public void play() {
        SelenideElement pauseButton = baseElement.$x(".//button[@data-testid='ui-play']");
        if (pauseButton.exists()) {
            pauseButton.click();
        }
    }

    public boolean isPaused() {
        try {
            return baseElement.$x(".//button[@data-testid='ui-play']//use[@xlink:href='#IconDsPlayerPlayFilled']").exists();
        } catch (Exception e) {
            return false;
        }
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
}