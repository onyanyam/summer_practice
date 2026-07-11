package pages;

import static com.codeborne.selenide.Selenide.$x;

public class PlaylistPage extends BasePage {

    // Конструктор для страницы "Понравилось"
    public PlaylistPage() {
        super(PlaylistPage.class, "//section[contains(@class, 'wdp-my-liked-module__content')]", "");
    }

    // Конструктор для страницы "Смотреть позже"
    public PlaylistPage(String type) {
        super(PlaylistPage.class, "//section[contains(@class, 'wdp-my-watch-later-module__grid')]", "");
    }

    public boolean isVideoInPlaylist(String videoTitle) {
        return $x(String.format("//*[contains(text(), '%s')]", videoTitle)).exists();
    }

    public boolean isVideoNotInPlaylist(String videoTitle) {
        return !$x(String.format("//*[contains(text(), '%s')]", videoTitle)).exists();
    }
}