package pages;

import elements.Button;
import elements.Link;
import elements.VideoPlayer;

import static com.codeborne.selenide.Selenide.$x;

public class VideoPage extends BasePage {

    private final VideoPlayer videoPlayer = new VideoPlayer();
    private final Button shareButton = Button.byXpath("//button[.//span[contains(text(), 'Поделиться')]]");
    private final Button copyLinkButton = Button.byXpath("//button[@aria-label='copy']");
    private final Button menuButton = Button.byXpath("//button[@data-testid='menu-action-video-row']");
    private final Button reportButton = Button.byXpath("//a[contains(@class, 'wdp-complaint-link-module__link') and contains(., 'Пожаловаться')]");
    private final Link channelLink = Link.byClass("wdp-video-options-row-module__author");
    private final Button likeButton = Button.byXpath("//button[@title='Нравится']");
    private final Button dislikeButton = Button.byXpath("//button[@title='Не нравится']");

    public VideoPage() {
        super(VideoPage.class, "//section[contains(@class, 'video-page-layout-module__player')]", "");
    }

    public VideoPage pause() {
        videoPlayer.pause();
        return this;
    }

    public boolean isPaused() {
        return videoPlayer.isPaused();
    }

    public VideoPage setQuality(String quality) {
        videoPlayer.setQuality(quality);
        return this;
    }

    public VideoPage toggleFullscreen() {
        videoPlayer.toggleFullscreen();
        return this;
    }

    public VideoPage share() {
        videoPlayer.share();
        return this;
    }

    public VideoPage copyLink() {
        shareButton.click();
        copyLinkButton.click();
        return this;
    }

    public VideoPage like() {
        likeButton.click();
        return this;
    }

    public VideoPage dislike() {
        dislikeButton.click();
        return this;
    }

    public VideoPage openMenu() {
        menuButton.click();
        return this;
    }

    public VideoPage reportVideo() {
        openMenu();
        reportButton.click();
        return this;
    }

    public ChannelPage goToChannel() {
        channelLink.click();
        return new ChannelPage();
    }
}