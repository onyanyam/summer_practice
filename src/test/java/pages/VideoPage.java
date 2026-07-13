package pages;

import elements.Button;
import elements.Link;
import elements.VideoPlayer;

import static com.codeborne.selenide.Selenide.$x;

/**
 * Класс, представляющий страницу с видео.
 * Содержит методы для взаимодействия с видео
 * (пауза, качество, лайк/дизлайк, поделиться)
 */
public class VideoPage extends BasePage {

    private final VideoPlayer videoPlayer = new VideoPlayer();
    private final Button shareButton = Button.byXpath("//button[.//span[contains(text(), 'Поделиться')]]");
    private final Button copyLinkButton = Button.byXpath("//button[@aria-label='copy']");
    private final Button menuButton = Button.byXpath("//button[@data-testid='menu-action-video-row']");
    private final Button reportButton = Button.byXpath("//a[contains(@class, 'wdp-complaint-link-module__link') and contains(., 'Пожаловаться')]");
    private final Link channelLink = Link.byClass("wdp-video-options-row-module__author");
    private final Button likeButton = Button.byXpath("//button[@title='Нравится']");
    private final Button dislikeButton = Button.byXpath("//button[@title='Не нравится']");

    /**
     * Конструктор страницы с видео
     */
    public VideoPage() {
        super(VideoPage.class, "//section[contains(@class, 'video-page-layout-module__player')]", "");
    }

    /**
     * Приостанавливает воспроизведение видео
     */
    public VideoPage pause() {
        videoPlayer.pause();
        return this;
    }

    /**
     * Проверяет, находится ли видео в состоянии паузы
     */
    public boolean isPaused() {
        return videoPlayer.isPaused();
    }

    /**
     * Изменяет качество воспроизведения видео
     */
    public VideoPage setQuality(String quality) {
        videoPlayer.setQuality(quality);
        return this;
    }

    /**
     * Переключает полноэкранный режим воспроизведения видео
     */
    public VideoPage toggleFullscreen() {
        videoPlayer.toggleFullscreen();
        return this;
    }

    /**
     * Открывает меню "Поделиться" для текущего видео
     */
    public VideoPage share() {
        videoPlayer.share();
        return this;
    }

    /**
     * Копирует ссылку на текущее видео
     */
    public VideoPage copyLink() {
        shareButton.click();
        copyLinkButton.click();
        return this;
    }

    /**
     * Ставит отметку "Нравится" под видео
     */
    public VideoPage like() {
        likeButton.click();
        return this;
    }

    /**
     * Ставит отметку "Не нравится" под видео
     */
    public VideoPage dislike() {
        dislikeButton.click();
        return this;
    }

    /**
     * Открывает контекстное меню (три точки) под видео
     */
    public VideoPage openMenu() {
        menuButton.click();
        return this;
    }

    /**
     * Выполняет последовательность действий для открытия формы жалобы на видео
     */
    public VideoPage reportVideo() {
        openMenu();
        reportButton.click();
        return this;
    }

    /**
     * Переходит на страницу канала автора видео
     */
    public ChannelPage goToChannel() {
        channelLink.click();
        return new ChannelPage();
    }
}