package pages;

import base.BasePage;
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

    private static final String SHARE_BUTTON_XPATH =
            "//button[.//span[contains(text(), 'Поделиться')]]";

    private static final String COPY_LINK_BUTTON_XPATH =
            "//button[@aria-label='copy']";

    private static final String MENU_BUTTON_XPATH =
            "//button[@data-testid='menu-action-video-row']";

    private static final String REPORT_BUTTON_XPATH =
            "//a[contains(@class, 'wdp-complaint-link-module__link') and contains(., 'Пожаловаться')]";

    private static final String LIKE_BUTTON_XPATH =
            "//button[@title='Нравится']";

    private static final String DISLIKE_BUTTON_XPATH =
            "//button[@title='Не нравится']";


    private static final String CHANNEL_LINK_CLASS =
            "wdp-video-options-row-module__author";

    private static final String VIDEO_PLAYER_LAYOUT =
            "//section[contains(@class, 'video-page-layout-module__player')]";


    private final VideoPlayer videoPlayer = VideoPlayer.getPlayer();
    private final Button shareButton = Button.byXpath(SHARE_BUTTON_XPATH);
    private final Button copyLinkButton = Button.byXpath(COPY_LINK_BUTTON_XPATH);
    private final Button menuButton = Button.byXpath(MENU_BUTTON_XPATH);
    private final Button reportButton = Button.byXpath(REPORT_BUTTON_XPATH);
    private final Link channelLink = Link.byClass(CHANNEL_LINK_CLASS);
    private final Button likeButton = Button.byXpath(LIKE_BUTTON_XPATH);
    private final Button dislikeButton = Button.byXpath(DISLIKE_BUTTON_XPATH);

    public VideoPage() {
        super(VideoPage.class, VIDEO_PLAYER_LAYOUT, "");
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