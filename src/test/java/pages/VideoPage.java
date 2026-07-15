package pages;

import base.BasePage;
import com.codeborne.selenide.Selenide;
import elements.Button;
import elements.Link;
import elements.VideoPlayer;

import static com.codeborne.selenide.Selenide.$x;

/**
 * Класс, представляющий страницу с видео.
 * Содержит методы для взаимодействия с элементами страницы,
 * окружающими плеер: лайки, кнопки "Поделиться", "Копировать ссылку",
 * контекстное меню (три точки), "Смотреть позже", жалоба.
 * Все действия внутри плеера делегируются VideoPlayer.
 */
public class VideoPage extends BasePage {

    private static final String SHARE_BUTTON_XPATH =
            "//button[.//span[contains(text(), 'Поделиться')]]";

    private static final String MENU_BUTTON_XPATH =
            "//button[@data-testid='menu-action-video-row']";

    private static final String WATCH_LATER_BUTTON_XPATH =
            "//button[contains(., 'Смотреть позже')]";

    private static final String COPY_LINK_BUTTON_XPATH =
            "//button[@aria-label='copy']";

    private static final String REPORT_BUTTON_XPATH =
            "//a[contains(@class, 'wdp-complaint-link-module__link') and contains(., 'Пожаловаться')]";

    private static final String SUCCESS_NOTIFICATION_XPATH =
            "//div[contains(@class, 'toast') and contains(., 'Жалоба отправлена')]";

    private static final String COPY_NOTIFICATION_XPATH =
            "//div[contains(@class, 'toast') and contains(., 'Ссылка скопирована')]";

    private static final String LIKE_BUTTON_XPATH =
            "//button[@title='Нравится']";

    private static final String DISLIKE_BUTTON_XPATH =
            "//button[@title='Не нравится']";

    private static final String CHANNEL_LINK_CLASS =
            "wdp-video-options-row-module__author";

    private static final String VIDEO_PLAYER_LAYOUT =
            "//section[contains(@class, 'video-page-layout-module__player')]";

    private static final String CURRENT_QUALITY_XPATH =
            "//span[contains(@class, 'current-quality')]";

    private static final String FULLSCREEN_CLASS = "fullscreen";

    private final VideoPlayer videoPlayer = VideoPlayer.getPlayer();

    private final Button shareButton = Button.byXpath(SHARE_BUTTON_XPATH);
    private final Button menuButton = Button.byXpath(MENU_BUTTON_XPATH);
    private final Button copyLinkButton = Button.byXpath(COPY_LINK_BUTTON_XPATH);
    private final Button reportButton = Button.byXpath(REPORT_BUTTON_XPATH);
    private final Link channelLink = Link.byClass(CHANNEL_LINK_CLASS);
    private final Button likeButton = Button.byXpath(LIKE_BUTTON_XPATH);
    private final Button dislikeButton = Button.byXpath(DISLIKE_BUTTON_XPATH);

    public VideoPage() {
        super(VideoPage.class, VIDEO_PLAYER_LAYOUT, "");
    }

    /**
     * Приостанавливает воспроизведение видео.
     * Делегирует вызов VideoPlayer.
     */
    public VideoPage pause() {
        videoPlayer.pause();
        return this;
    }

    /**
     * Проверяет, находится ли видео на паузе.
     * Делегирует вызов VideoPlayer.
     */
    public boolean isPaused() {
        return videoPlayer.isPaused();
    }

    /**
     * Изменяет качество видео.
     * Делегирует вызов VideoPlayer.
     */
    public VideoPage setQuality(String quality) {
        videoPlayer.setQuality(quality);
        return this;
    }

    /**
     * Переключает полноэкранный режим.
     * Делегирует вызов VideoPlayer.
     */
    public VideoPage toggleFullscreen() {
        videoPlayer.toggleFullscreen();
        return this;
    }

    /**
     * Открывает окно "Поделиться".
     * Кнопка "Поделиться" находится под плеером.
     */
    public VideoPage share() {
        shareButton.click();
        return this;
    }

    /**
     * Открывает контекстное меню (три точки).
     * Кнопка меню находится под плеером.
     */
    public VideoPage openMenu() {
        menuButton.click();
        return this;
    }

    /**
     * Добавляет видео в "Смотреть позже".
     * Открывает меню и кликает по пункту.
     */
    public VideoPage addToWatchLater() {
        openMenu();
        Button.byXpath(WATCH_LATER_BUTTON_XPATH).click();
        return this;
    }

    /**
     * Копирует ссылку на текущее видео.
     * Открывает окно "Поделиться" (под плеером), затем кликает по кнопке "Копировать ссылку".
     */
    public VideoPage copyLink() {
        shareButton.click();
        copyLinkButton.click();
        return this;
    }

    /**
     * Проверяет, что ссылка скопирована.
     */
    public boolean isLinkCopied() {
        String clipboardText = Selenide.clipboard().getText();

        return clipboardText != null && clipboardText.contains("rutube.ru");
    }

    /**
     * Ставит лайк.
     */
    public VideoPage like() {
        likeButton.click();
        return this;
    }

    /**
     * Ставит дизлайк.
     */
    public VideoPage dislike() {
        dislikeButton.click();
        return this;
    }

    /**
     * Отправляет жалобу на видео.
     * Открывает меню, затем кликает "Пожаловаться".
     */
    public VideoPage reportVideo() {
        openMenu();
        reportButton.click();
        return this;
    }

    /**
     * Проверяет наличие уведомления об успешной отправке жалобы.
     */
    public boolean isComplaintSent() {
        return $x(SUCCESS_NOTIFICATION_XPATH).isDisplayed();
    }

    /**
     * Переходит на страницу канала автора.
     */
    public ChannelPage goToChannel() {
        channelLink.click();
        return new ChannelPage();
    }

    /**
     * Возвращает название видео (из заголовка на странице).
     */
    public String getVideoTitle() {
        return $x("//h1").getText();
    }

    /**
     * Возвращает текущее качество видео.
     */
    public String getCurrentQuality() {
        return $x(CURRENT_QUALITY_XPATH).getText();
    }

    /**
     * Проверяет, включён ли полноэкранный режим.
     */
    public boolean isFullscreen() {
        return $x("//section[contains(@class, '" + FULLSCREEN_CLASS + "')]").exists();
    }
}