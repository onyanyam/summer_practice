package pages;

import static com.codeborne.selenide.Selenide.$x;

/**
 * Класс, представляющий страницы плейлистов на Rutube.
 * Поддерживает работу с различными типами плейлистов:
 * "Понравилось", "Смотреть позже" и др.
 */
public class PlaylistPage extends BasePage {

    /**
     * Конструктор для страницы плейлиста "Понравилось".
     */
    public PlaylistPage() {
        super(PlaylistPage.class, "//section[contains(@class, 'wdp-my-liked-module__content')]", "");
    }

    /**
     * Конструктор для страницы плейлиста с указанием типа.
     * Используется для различных типов плейлистов, например "Смотреть позже".
     */
    public PlaylistPage(String type) {
        super(PlaylistPage.class, "//section[contains(@class, 'wdp-my-watch-later-module__grid')]", "");
    }

    /**
     * Проверяет, присутствует ли видео с указанным названием в плейлисте.
     * Ищет элемент с текстом, совпадающим с названием видео.
     */
    public boolean isVideoInPlaylist(String videoTitle) {
        return $x(String.format("//*[contains(text(), '%s')]", videoTitle)).exists();
    }

    /**
     * Проверяет, отсутствует ли видео с указанным названием в плейлисте.
     * Ищет элемент с текстом, совпадающим с названием видео.
     */
    public boolean isVideoNotInPlaylist(String videoTitle) {
        return !$x(String.format("//*[contains(text(), '%s')]", videoTitle)).exists();
    }
}