package pages;

import base.BasePage;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import elements.Button;
import elements.Link;
import elements.VideoPlayer;

import java.time.Duration;
import java.util.Set;

import static com.codeborne.selenide.Condition.visible;
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
            "//a[contains(@class, 'wdp-complaint-link-module__link')]";

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
    private final Button menuButton = Button.byXpath(MENU_BUTTON_XPATH);
    private final Button copyLinkButton = Button.byXpath(COPY_LINK_BUTTON_XPATH);
    private final Link channelLink = Link.byClass(CHANNEL_LINK_CLASS);
    private final Button likeButton = Button.byXpath(LIKE_BUTTON_XPATH);
    private final Button dislikeButton = Button.byXpath(DISLIKE_BUTTON_XPATH);

    public VideoPage() {
        super(VideoPage.class, VIDEO_PLAYER_LAYOUT, "");
    }

    private void switchToNewWindow() {
        String mainWindow = WebDriverRunner.getWebDriver().getWindowHandle();
        Set<String> handles = WebDriverRunner.getWebDriver().getWindowHandles();
        for (String handle : handles) {
            if (!handle.equals(mainWindow)) {
                WebDriverRunner.getWebDriver().switchTo().window(handle);
                break;
            }
        }
    }

    public VideoPlayer getVideoPlayer() {
        return videoPlayer;
    }

    /**
     * Возвращает текущее качество видео.
     */
    public String getCurrentQuality() {
        return videoPlayer.getCurrentQuality();
    }

    /**
     * Проверяет, находится ли видео на паузе.
     */
    public boolean isPaused() {
        return videoPlayer.isPaused();
    }

    /**
     * Изменяет качество видео.
     */
    public VideoPage setQuality(String quality) {
        videoPlayer.setQuality(quality);
        return this;
    }

    public void pause() {
            videoPlayer.pause();
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
        SelenideElement reportLink = $x(REPORT_BUTTON_XPATH);
        reportLink.shouldBe(visible, Duration.ofSeconds(5));
        reportLink.click();
        switchToNewWindow();
        return this;
    }

    public boolean isComplaintFormDisplayed() {
        try {
            SelenideElement title = $x("//*[contains(text(), 'Сообщение о контенте с недопустимым содержанием')]");
            title.shouldBe(visible, Duration.ofSeconds(10));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public VideoPage closeComplaintForm() {
        WebDriverRunner.getWebDriver().close();
        Set<String> handles = WebDriverRunner.getWebDriver().getWindowHandles();
        if (!handles.isEmpty()) {
            WebDriverRunner.getWebDriver().switchTo().window(handles.iterator().next());
        }
        return this;
    }

    /**
     * Возвращает название видео (из заголовка на странице).
     */
    public String getVideoTitle() {
        return $x("//h1").getText();
    }

    /**
     * Проверяет, включён ли полноэкранный режим.
     */
    public boolean isFullscreen() {
        try {
            SelenideElement fullscreenBtn = $x("//button[@data-testid='ui-fullscreen']");
            String ariaLabel = fullscreenBtn.getAttribute("aria-label");

            return ariaLabel != null && ariaLabel.contains("Выйти из полноэкранного режима");
        } catch (Exception e) {
            return false;
        }
    }
}