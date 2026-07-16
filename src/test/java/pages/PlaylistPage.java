package pages;

import base.BasePage;
import com.codeborne.selenide.Selenide;

import static com.codeborne.selenide.Selenide.$x;

/**
 * Класс, представляющий страницы плейлистов на Rutube.
 * Поддерживает работу с различными типами плейлистов:
 * "Понравилось", "Смотреть позже" и др.
 */
public class PlaylistPage extends BasePage {

    private static final String LIKED_PLAYLIST_XPATH =
            "//section[contains(@class, 'wdp-my-liked-module__content')]";

    private static final String WATCH_LATER_PLAYLIST_XPATH =
            "//section[contains(@class, 'wdp-my-watch-later-module__grid')]";

    private static final String VIDEO_TITLE_XPATH =
            "//*[contains(text(), '%s')]";

    private static final String RUTUBE_LIKED_LINK = "https://rutube.ru/my/liked/";

    private PlaylistPage(String rootXpath) {
        super(PlaylistPage.class, rootXpath, "");
    }

    /**
     * Создаёт экземпляр страницы плейлиста "Понравилось".
     */
    public static PlaylistPage likedPlaylist() {
        Selenide.open(RUTUBE_LIKED_LINK);
        return new PlaylistPage(LIKED_PLAYLIST_XPATH);
    }

    /**
     * Создаёт экземпляр страницы плейлиста "Смотреть позже".
     */
    public static PlaylistPage watchLaterPlaylist() {
        return new PlaylistPage(WATCH_LATER_PLAYLIST_XPATH);
    }

    /**
     * Проверяет, присутствует ли видео с указанным названием в плейлисте.
     * Ищет элемент с текстом, совпадающим с названием видео.
     */
    public boolean isVideoInPlaylist(String videoTitle) {
        return $x(String.format(VIDEO_TITLE_XPATH, videoTitle)).exists();
    }

    /**
     * Проверяет, отсутствует ли видео с указанным названием в плейлисте.
     * Ищет элемент с текстом, совпадающим с названием видео.
     */
    public boolean isVideoNotInPlaylist(String videoTitle) {
        return !$x(String.format(VIDEO_TITLE_XPATH, videoTitle)).exists();
    }
}